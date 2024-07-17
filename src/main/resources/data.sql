
INSERT INTO cinema_schema.genre(id,"name") VALUES
                                               ('37bb059c-4d18-4caa-9ddf-152ba8dfab8b','Name1'),
                                               ('7c2ffbf3-d2cb-4229-bf89-387ff7d512e4','Name2'),
                                               ('a5439166-1969-434e-9910-f30bafc5b796','Name3');

INSERT INTO cinema_schema.actor(id,birthdate,information,"name",actor_imageurl) VALUES
                                                                    ('c77ad2be-0281-4458-9d66-892393ea4999','1980-11-12','Канадский актёр. Известен по ролям в независимых фильмах и крупных студийных проектах разных жанров. Сборы фильмов с его участием составили свыше 1,9 миллиарда долларов. Обладатель многих наград[англ.], включая «Золотого глобуса», и номинант трёх «Оскаров» и одного BAFTA. ','Райан Гослинг','http://localhost:8080/api/v1/files/rayan_gosling.jpg'),
                                                                    ('1aecd551-1a35-4fa3-ac2b-a0302bcb8a21','1963-12-18','Американский актёр и кинопродюсер. Лауреат двух премий «Золотой глобус», двух премий Американской Гильдии киноактёров и двух наград Британской киноакадемии. Обладатель премии «Оскар» как один из продюсеров фильма «12 лет рабства» — победителя в категории «Лучший фильм» на церемонии 2014 года — и за лучшую мужскую роль второго плана в картине «Однажды в Голливуде» (2020)[1]. До этого пять раз номинировался на премию «Оскар» (трижды — как актёр и два раза — как продюсер). ','Брэд Питт','http://localhost:8080/api/v1/files/brad_pitt.jpg'),
                                                                    ('2decd551-1a35-4fa3-ac2b-a0302bcb8a97','1964-09-02','Канадский актёр, кинорежиссёр, кинопродюсер и музыкант (бас-гитарист). Наиболее известен своими ролями в киносериях «Билл и Тед»[англ.] (1989—2020), «Матрица» (1999—2021) и «Джон Уик» (с 2014), а также в фильмах «На гребне волны» (1991), «Мой личный штат Айдахо» (1991), «Дракула» (1992), «Скорость» (1994), «Джонни-мнемоник» (1995), «Адвокат дьявола» (1997), «Константин: Повелитель тьмы» (2005), «Короли улиц» (2008) и «47 ронинов» (2013). ','Киану Ривз','http://localhost:8080/api/v1/files/keanu_reeves.jpg'),
                                                                    ('f1515473-464f-4d33-a7c4-3caccfe140be','1956-03-07','Американский актёр телевидения, кино и озвучивания, режиссёр, продюсер и сценарист. Номинант на премии «Оскар» и BAFTA, лауреат двух премий «Тони», шести премий «Эмми», двух премий «Золотой глобус», а также пяти наград Американской Гильдии киноактёров. Обладатель именной звезды на Голливудской «Аллее славы».

Широко известен как исполнитель главной роли учителя химии Уолтера Уайта в телесериале канала AMC «Во все тяжкие», ранее был известен на телевидении как исполнитель главной роли в ситкоме «Малкольм в центре внимания». В кино Крэнстон снискал признание за создание ключевых образов в картинах «Драйв», «Вспомнить всё» и «Операция „Арго“».','Брайан Крэнстон','http://localhost:8080/api/v1/files/brayan_cranston.jpg'),
                                                                    ('d2515473-464f-4d33-a7c4-3caccfe140be','1985-05-28','Английская актриса. Обладательница награды Британской академии «BAFTA» в категории «Лучшая актриса» и номинантка в категории «Восходящая звезда» (2010), трёхкратная номинантка на премию «Оскар» (2010, 2021, 2024)[1], четырёхкратная номинантка на премию «Золотой глобус» (2010, 2021, 2023, 2024)[2] и премию Американской Гильдии киноактёров (2010 — дважды, 2021) за главные роли в картинах «Воспитание чувств» и «Девушка, подающая надежды». Четырёхкратная номинантка на награду «BAFTA» (2010 — дважды, 2012, 2023) за роли в картинах «Воспитание чувств», «Драйв» и «Её правда». Помимо работы в Великобритании, активно снимается в кино и играет на театральных сценах Лондона и Бродвея. Главная роль в лондонской постановке британского драматурга Дэвида Хэйра «Небесный свет» на Бродвее принесла Кэри Маллиган номинацию на самую главную американскую театральную награду «Тони» в категории «Лучшая актриса главной роли в пьесе» в 2015 году. В некоторых фильмах со своим участием также была задействована в качестве вокалистки («Стыд», «Внутри Льюина Дэвиса» и т. д.).','Кэри Маллиган','http://localhost:8080/api/v1/files/carey_mulligan.jpg');


INSERT INTO cinema_schema.subscription(id,cost,end_date,start_date) VALUES
                                                                        ('8f8e1169-46b8-4abd-b1af-9f2f46dad773',300.00,'2024-01-01','2024-02-01'),
                                                                        ('f71b18a9-2b6a-4c1b-a915-90018739c3bf',200.00,'2024-02-02','2024-03-01'),
                                                                        ('e9281520-8791-4630-90da-81f3c47ee8e4',100.00,'2024-03-03','2024-04-01');

INSERT INTO cinema_schema.app_user(id,email,"name","password",phone,subscription_id,profile_imageurl) VALUES
                                                                                         ('014128c1-4c97-4f3c-a020-35219c85ba32','test@test.test','Ivan','$2y$12$x/QJ1QjFWVc/7bfQpUWVU.iJCPbvsW7fBmg7QVtiGNhiECCkN6iQG','89997776655','8f8e1169-46b8-4abd-b1af-9f2f46dad773','http://localhost:8080/api/v1/files/default_profile_image.jpg'),
                                                                                         ('f3b2365f-ab4f-497a-95f7-c5ecdea68396','test1@test.test','Vlad','$2y$12$nmXTHE7QTsUNgaTrnZtBauN16YEmCDMlZfk.for734wIgxCNBf6Hu','81117776655','f71b18a9-2b6a-4c1b-a915-90018739c3bf','http://localhost:8080/api/v1/files/default_profile_image.jpg'),
                                                                                         ('ba41512c-a21b-46d5-8628-9b24eba24a12','test2@test.test','Alex','$2y$12$cHlf2n72i.NH92EzNEoEvuSK5PAS9BKHDDw1wWxJxGZvfDfrfG896','89227776655','e9281520-8791-4630-90da-81f3c47ee8e4','http://localhost:8080/api/v1/files/default_profile_image.jpg');

INSERT INTO cinema_schema.director(id,birthdate,information,"name") VALUES
                                                                        ('f7f39016-e209-41f5-9e49-eef23eff3a68','1970-09-29','Датский кинорежиссёр, сценарист, продюсер и актёр. Обладатель приза за лучшую режиссуру на 64-м Каннском кинофестивале[3], номинант на премию BAFTA в категории «Лучшая режиссёрская работа» за фильм «Драйв».','Николас Виндинг Рефн'),
                                                                        ('42a8e31d-aac5-4707-9955-6cbffc53ab13','2024-03-02','2Lorem ipsum dolor sit amet','Name2');

INSERT INTO cinema_schema.film(id,"cost",description,"resource_link",release_date,title,director_id,preview_imageurl) VALUES
                                                                                                         ('659cdb4d-c174-40d6-a5e3-eecc46bed3cf',123,'Американский криминальный нео-нуарный триллер с элементами боевика датского режиссёра Николаса Виндинга Рефна с Райаном Гослингом в главной роли. Картина снята по одноимённому роману Джеймса Сэллиса, сценарий был написан Хуссейном Амини. Фильм является победителем Каннского кинофестиваля 2011 года в номинации «Лучшая режиссура». В США фильм вышел в прокат 16 сентября 2011 года, в России — 3 ноября.

Лента Рефна наполнена всевозможными отсылками к классике киноэкрана 1970—1980-х годов, в частности, к «Дню саранчи[англ.]» и «Жить и умереть в Лос-Анджелесе». По признанию режиссёра, во время разработки «Драйва» он вдохновлялся фильмами «Выстрел в упор», «Двухполосное шоссе» и «Водитель». Утончённая операторская работа Ньютона Т. Сигела отдаёт дань уважения работам Жана-Пьера Мельвиля.

Картина была благосклонно воспринята большинством мировых кинокритиков, в особенности были отмечены актёрские работы Райана Гослинга и Альберта Брукса, а также крайне удачно подобранный музыкальный ряд. «Драйв» был одним из основных претендентов на премию «Оскар» в основных категориях, но киноакадемики выдвинули его в единственной технической номинации «Лучший звуковой монтаж».Перейти к разделу Сотрудничество Гослинга и Рефна позже вылилось в новый проект режиссёра — триллер «Только Бог простит» (2013), в котором Рефн продолжает развивать тематику, затронутую в «Драйве».','path1','2024-02-01','Драйв','f7f39016-e209-41f5-9e49-eef23eff3a68','http://localhost:8080/api/v1/files/default_preview_image.jpg'),
                                                                                                         ('7b3d033d-bfd1-4d59-b4a9-893de91bf0d6',123,'Lorem ipsum dolor sit amet','path2','2024-05-05','Title2','42a8e31d-aac5-4707-9955-6cbffc53ab13','http://localhost:8080/api/v1/files/default_preview_image.jpg'),
                                                                                                         ('77384c6c-f990-451f-a8bd-a67f10ef75b9',123,'Lorem ipsum dolor sit amet','path3','2024-03-06','Title3','42a8e31d-aac5-4707-9955-6cbffc53ab13','http://localhost:8080/api/v1/files/default_preview_image.jpg'),
                                                                                                         ('15467cdf-e85a-4857-911f-334ccc948f4d',123,'Lorem ipsum dolor sit amet','path4','2024-01-02','Title4','f7f39016-e209-41f5-9e49-eef23eff3a68','http://localhost:8080/api/v1/files/default_preview_image.jpg');

INSERT INTO cinema_schema.series(id,"cost",description,release_date,title,director_id,preview_imageurl) VALUES
    ('e4f7e3dc-b18e-4690-bb64-2748b78ad7bb',150.00,'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.','2024-02-01','title1','f7f39016-e209-41f5-9e49-eef23eff3a68','http://localhost:8080/api/v1/files/default_preview_image.jpg');

INSERT INTO cinema_schema.season(id,description,"title",release_date,series_id,preview_imageurl) VALUES
                                                                                  ('74b67f02-f716-468c-a7f8-08615dd91e00','1Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.','Name1','2024-02-01','e4f7e3dc-b18e-4690-bb64-2748b78ad7bb','http://localhost:8080/api/v1/files/default_preview_image.jpg'),
                                                                                  ('377a9d12-3ca7-4497-8ec7-5a57de79b5ac','2Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.','Name2','2024-03-01','e4f7e3dc-b18e-4690-bb64-2748b78ad7bb','http://localhost:8080/api/v1/files/default_preview_image.jpg'),
                                                                                  ('f5e4efd1-67a2-4409-8a29-cd1a9f8c6dfd','3Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.','Name3','2024-04-01','e4f7e3dc-b18e-4690-bb64-2748b78ad7bb','http://localhost:8080/api/v1/files/default_preview_image.jpg');

INSERT INTO cinema_schema.episode(id,description,"resource_link",release_date,title,season_id,preview_imageurl) VALUES
                                                                                                   ('f6ebf7f2-85b1-4950-8344-cff7d07b285c','1Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.','path1','2024-02-01','title1','74b67f02-f716-468c-a7f8-08615dd91e00','http://localhost:8080/api/v1/files/default_preview_image.jpg'),
                                                                                                   ('3b4d4037-9dfa-4aa0-a0b8-98b7b7b6ee01','5Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.','path6','2024-06-01','title2','74b67f02-f716-468c-a7f8-08615dd91e00','http://localhost:8080/api/v1/files/default_preview_image.jpg'),
                                                                                                   ('e1a55cdf-80f4-4c33-9afe-08655ae023d2','2Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.','path2','2024-03-01','title1','377a9d12-3ca7-4497-8ec7-5a57de79b5ac','http://localhost:8080/api/v1/files/default_preview_image.jpg'),
                                                                                                   ('22e9c61e-7deb-4956-9d57-7488bb35b9a4','3Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.','path3','2024-04-01','title1','f5e4efd1-67a2-4409-8a29-cd1a9f8c6dfd','http://localhost:8080/api/v1/files/default_preview_image.jpg');


INSERT INTO cinema_schema.rating(id,"comment",rating,film_id,series_id,user_id) VALUES
                                                                                    ('4c2a57bf-2469-411e-be07-ba2eac1dd62c','1Lorem ipsum dolor sit amet',5,'659cdb4d-c174-40d6-a5e3-eecc46bed3cf',NULL,'014128c1-4c97-4f3c-a020-35219c85ba32'),
                                                                                    ('da165cd1-fa6b-49cd-b1f9-4f462ea6b28c','2Lorem ipsum dolor sit amet',5,NULL,'e4f7e3dc-b18e-4690-bb64-2748b78ad7bb','014128c1-4c97-4f3c-a020-35219c85ba32');

INSERT INTO cinema_schema.film_genre(film_id,genre_id) VALUES
                                                           ('659cdb4d-c174-40d6-a5e3-eecc46bed3cf','37bb059c-4d18-4caa-9ddf-152ba8dfab8b'),
                                                           ('659cdb4d-c174-40d6-a5e3-eecc46bed3cf','7c2ffbf3-d2cb-4229-bf89-387ff7d512e4'),
                                                           ('659cdb4d-c174-40d6-a5e3-eecc46bed3cf','a5439166-1969-434e-9910-f30bafc5b796'),
                                                           ('7b3d033d-bfd1-4d59-b4a9-893de91bf0d6','37bb059c-4d18-4caa-9ddf-152ba8dfab8b'),
                                                           ('7b3d033d-bfd1-4d59-b4a9-893de91bf0d6','7c2ffbf3-d2cb-4229-bf89-387ff7d512e4'),
                                                           ('77384c6c-f990-451f-a8bd-a67f10ef75b9','a5439166-1969-434e-9910-f30bafc5b796'),
                                                           ('15467cdf-e85a-4857-911f-334ccc948f4d','a5439166-1969-434e-9910-f30bafc5b796');

INSERT INTO cinema_schema.series_genre(series_id,genre_id) VALUES
                                                               ('e4f7e3dc-b18e-4690-bb64-2748b78ad7bb','37bb059c-4d18-4caa-9ddf-152ba8dfab8b'),
                                                               ('e4f7e3dc-b18e-4690-bb64-2748b78ad7bb','7c2ffbf3-d2cb-4229-bf89-387ff7d512e4');

INSERT INTO cinema_schema.series_actors(series_id,actor_id) VALUES
                                                                ('e4f7e3dc-b18e-4690-bb64-2748b78ad7bb','c77ad2be-0281-4458-9d66-892393ea4999'),
                                                                ('e4f7e3dc-b18e-4690-bb64-2748b78ad7bb','f1515473-464f-4d33-a7c4-3caccfe140be');

INSERT INTO cinema_schema.film_actors(film_id,actor_id) VALUES
                                                            ('659cdb4d-c174-40d6-a5e3-eecc46bed3cf','c77ad2be-0281-4458-9d66-892393ea4999'),
                                                            ('659cdb4d-c174-40d6-a5e3-eecc46bed3cf','d2515473-464f-4d33-a7c4-3caccfe140be'),
                                                            ('659cdb4d-c174-40d6-a5e3-eecc46bed3cf','f1515473-464f-4d33-a7c4-3caccfe140be'),
                                                            ('7b3d033d-bfd1-4d59-b4a9-893de91bf0d6','f1515473-464f-4d33-a7c4-3caccfe140be'),
                                                            ('77384c6c-f990-451f-a8bd-a67f10ef75b9','2decd551-1a35-4fa3-ac2b-a0302bcb8a97'),
                                                            ('15467cdf-e85a-4857-911f-334ccc948f4d','c77ad2be-0281-4458-9d66-892393ea4999');
INSERT INTO cinema_schema.user_roles(user_id,roles) VALUES
                                                            ('014128c1-4c97-4f3c-a020-35219c85ba32','ROLE_ADMIN'),
                                                            ('f3b2365f-ab4f-497a-95f7-c5ecdea68396','ROLE_MODERATOR'),
                                                            ('ba41512c-a21b-46d5-8628-9b24eba24a12','ROLE_USER');