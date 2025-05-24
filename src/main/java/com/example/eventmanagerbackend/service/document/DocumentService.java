package com.example.eventmanagerbackend.service.document;


import com.deepoove.poi.XWPFTemplate;
import com.example.eventmanagerbackend.entity.Event;
import com.example.eventmanagerbackend.entity.EventMember;
import com.example.eventmanagerbackend.entity.TemplateEntity;
import com.example.eventmanagerbackend.entity.Type;
import com.example.eventmanagerbackend.exception.EntityNotFoundException;
import com.example.eventmanagerbackend.repository.EventMemberRepository;
import com.example.eventmanagerbackend.repository.EventRepository;
import com.example.eventmanagerbackend.service.EventService;

import com.example.eventmanagerbackend.service.template.TemplateService;
import com.example.eventmanagerbackend.utils.qr_generator.QrCodeGenerator;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;
import java.util.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class DocumentService {

    private final TemplateService templateService;
    private final String BADGE_TEMPLATE_PATH = "src/main/resources/static/badge_template.docx";
    private final EventRepository eventRepository;
    private final EventMemberRepository eventMemberRepository;
    private final SpringTemplateEngine templateEngine;

    private final String templatesPath = "templates/";

    private final String baseUrl;

    public DocumentService(@Value("${base-url}") String baseUrl,
                           TemplateService templateService,
                           EventRepository eventRepository,
                           EventMemberRepository eventMemberRepository,
                           SpringTemplateEngine templateEngine) {
        this.baseUrl = baseUrl;
        this.templateService = templateService;
        this.eventRepository = eventRepository;
        this.eventMemberRepository = eventMemberRepository;
        this.templateEngine = templateEngine;
    }


    public ByteArrayInputStream generatePdfQrReport(Map<String, Object> context)
            throws DocumentException, IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        QrCodeGenerator qrCodeGenerator = new QrCodeGenerator();
        Optional<EventMember> member = eventMemberRepository.findById((UUID) context.get("memberId"));
        String templateContent = "";

        if (member.isPresent()) {
            EventMember eventMember = member.get();
            Optional<TemplateEntity> templateOpt = templateService.getTemplateByEventIdAndType(eventMember.getEvent().getId(), Type.QR_PDF);

            if (templateOpt.isEmpty()) {
                try {
                    Resource resource = new ClassPathResource("templates/qr_pdf2.html");
                    templateContent = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to load default template", e);
                }
            } else {
                templateContent = templateOpt.get().getTemptext();
            }
        }

        String url = baseUrl + context.get("eventId") + "/members/" + context.get("memberId") + "/status";
        BufferedImage qrCodeImage = qrCodeGenerator.generateQrCode(url);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(qrCodeImage, "png", baos);
        String base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());
        String image = "data:image/png;base64," + base64Image;
        context.put("qr_image", image);

        ITextRenderer renderer = new ITextRenderer();
        URL url1 = new URL("classpath:/static/Manrope.ttf");
        URL url2 = new URL("classpath:/static/Unbounded.ttf");
        URL url3 = new URL("classpath:/static/Courier New.ttf");
        URL url4 = new URL("classpath:/static/Arial.ttf");
        String path1 = url1.getPath();
        String path2 = url2.getPath();
        String path3 = url3.getPath();
        String path4 = url4.getPath();
        renderer.getFontResolver().addFont(path1, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        renderer.getFontResolver().addFont(path2, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        renderer.getFontResolver().addFont(path3, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        renderer.getFontResolver().addFont(path4, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Resource resource = new ClassPathResource(templatesPath);
        //Path path = Paths.get(resource.getURI());

        String baseUrl = resource.getURL().toString();
        String htmlContent = generateHtml(templateContent, context);
        Document document = Jsoup.parse(htmlContent, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        renderer.setDocumentFromString(document.html(), baseUrl);
        renderer.layout();
        renderer.createPDF(outputStream, false);
        renderer.finishPDF();
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    public  ByteArrayInputStream generatePdfBadges(List<EventMember> members, String eventName)
            throws DocumentException, IOException {

        List<ArrayList<EventMember>> members_pairs = new ArrayList<>();
        for(int i = 0; i < members.size(); i += 2){
            ArrayList<EventMember> pair = new ArrayList<>();
            pair.add(members.get(i));
            if (i+1 < members.size()){
                pair.add(members.get(i+1));
            }
            members_pairs.add(pair);
        }



        Map<String, Object> context = new HashMap<>();
        context.put("members_pairs",members_pairs);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        context.put("event_name", eventName);

        ITextRenderer renderer = new ITextRenderer();

        URL url1 = new URL("classpath:/static/Manrope.ttf");
        URL url2 = new URL("classpath:/static/Unbounded.ttf");
        String path1 = url1.getPath();
        String path2 = url2.getPath();
        renderer.getFontResolver().addFont(path1, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        renderer.getFontResolver().addFont(path2, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        String htmlContent = generateHtmlOld("badge2.html", context);
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(outputStream, false);
        renderer.finishPDF();
        return new ByteArrayInputStream(outputStream.toByteArray());

    }

    public ByteArrayInputStream generateWordBadge(UUID eventMemberId)
            throws FileNotFoundException, IOException,
            InvalidFormatException {
            ByteArrayOutputStream bis = new ByteArrayOutputStream();

            EventMember eventMember = eventMemberRepository.findById(eventMemberId).orElseThrow(() -> new EntityNotFoundException(
                    MessageFormat.format("Event member with ID {0} not found!", id)
            ));
            Event event = eventRepository.findById(eventMember.getEvent().getId()).orElseThrow(
                    (()-> new RuntimeException(MessageFormat.format("Event with id {0} not found!",eventMember.getEvent().getId())))
            );

            XWPFTemplate.compile(BADGE_TEMPLATE_PATH).render(new HashMap<String, Object>(){{
                put("имя", eventMember.getFirstname());
                put("отчество", eventMember.getMiddlename());
                put("фамилия", eventMember.getLastname());
                put("email", eventMember.getEmail());
                put("телефон", eventMember.getPhone());
                put("должность", eventMember.getPosition());
                put("организация", eventMember.getCompany());
                put("статус", eventMember.getStatus().getStatus());

                put("ивент_дата", event.getDate());
                put("ивент_имя", event.getName());
                put("ивент_описание", event.getSummary());
                put("ивент_адрес", event.getAddress());
                put("role", "Участник");
            }}).writeAndClose(bis);

            return new ByteArrayInputStream(bis.toByteArray());


    }

    private String generateHtml(String templateContent, Map<String, Object> data) {
        // Убедитесь, что templateContent содержит правильный HTML
        System.out.println("Template Content:");
        System.out.println(templateContent);

        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(new StringReader(templateContent), "template");

        // Выполняем рендеринг шаблона
        StringWriter writer = new StringWriter();
        mustache.execute(writer, data);

        // Логируем результат рендеринга для проверки
        String htmlContent = writer.toString();
        System.out.println("Generated HTML Content:");
        System.out.println(htmlContent);

        return htmlContent;
    }

    private String generateHtmlOld(String templateFileName, Map<String, Object> data) {
        Context context = new Context();
        context.setVariables(data);
        return templateEngine.process(templateFileName, context);
    }


// UNUSED FUNCTIONS
    //
//    public  ByteArrayInputStream generatePdf(String qrFilename, Event_Member eventMember, Optional<Event> event) {
//        try {
//            PDDocument pdDoc = new PDDocument();
//            PDPage page = new PDPage();
//            // add page to the document
//            pdDoc.addPage(page);
//            // write to a page content stream
//            try(PDPageContentStream cs = new PDPageContentStream(pdDoc, page)){
//                cs.beginText();
//                Resource resource = new ClassPathResource("/static/arialmt.ttf");
//                PDType0Font font = PDType0Font.load(pdDoc, resource.getInputStream());
//                // setting font family and font size
//                cs.setFont(font, 14);
//                // Text color in PDF
//                cs.setNonStrokingColor(Color.BLUE);
//                // set offset from where content starts in PDF
//                cs.newLineAtOffset(20, 750);
//                String eventName;
//                if (event.isPresent()) {
//                    eventName = event.get().getEvent_name();
//                }
//                else  eventName = "ОШИБКА";
//
//                String text = String.format("Hello %s %s %s! You are invited to the event: %s",
//                        eventMember.getFirstname(), eventMember.getMiddlename(), eventMember.getLastname(),
//                        eventName);
//                cs.showText(text);
//                cs.endText();
//                QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
//                BufferedImage qrCodeImage = qrCodeGenerator.generateQrCode("https://www.youtube.com/watch?v=QGx0pP3Uk0c&ab_channel=%D0%90%D1%81%D0%B0%D1%84%D1%8C%D0%B5%D0%B2.%D0%96%D0%B8%D0%B7%D0%BD%D1%8C");
//                PDImageXObject pdImage = LosslessFactory.createFromImage(pdDoc, qrCodeImage);
//                cs.drawImage(pdImage, 200, -10);
//            }
//            // save and close PDF document
//            ByteArrayOutputStream b = new ByteArrayOutputStream();
//            pdDoc.save(b);
//            pdDoc.close();
//            return new ByteArrayInputStream(b.toByteArray());
//        } catch(IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public ByteArrayInputStream generateListOfWordBadges(UUID eventId)
//            throws FileNotFoundException, IOException,
//            InvalidFormatException {
//        ByteArrayOutputStream bis = new ByteArrayOutputStream();
//
//        List<Event_Member> members = new ArrayList<>();
//
//        members = eventService.findMembersByEventId(eventId);
//
//        if (members == null){
//            return null;
//        }
//
//
//
//
//
//        XWPFTemplate.compile("/home/vladimir/IdeaProjects/eventForIAC/src/main/resources/static/badge_template.docx").render(new HashMap<String, Object>(){{
//            put("first_name", eventMember.getFirstname());
//            put("last_name", eventMember.getLastname());
//            put("role", "Участник");
//            put("event", event.getEvent_name());
//        }}).writeAndClose(bis);
//
//        return new ByteArrayInputStream(bis.toByteArray());
//
//
//    }
}
