INSERT INTO event_schema.users VALUES
                                     ('6c81ae12-7bac-4eab-ae1a-160743edbdaf','$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i','admin');

INSERT INTO event_schema.roles VALUES
                                   ('d2dd39a5-b13c-426f-aa29-da82bb6dbf29','ROLE_ADMIN'),
                                   ('9506f129-5b3b-4b82-8202-75aefd1da713','ROLE_ORGANIZATOR'),
                                   ('f93d3f09-452a-4dc8-9b60-c3177242f069','ROLE_USER');

INSERT INTO event_schema.events VALUES
                                   ('603b961f-a315-4c6e-9305-bea0ef60a98c','address','2024-07-07 17:12:19.146000', '2024-07-07 17:12:19.146000','first_event',true,'2024-07-07 17:12:19.146000','idk','6c81ae12-7bac-4eab-ae1a-160743edbdaf');

INSERT INTO event_schema.users_roles VALUES
                                   ('6c81ae12-7bac-4eab-ae1a-160743edbdaf','d2dd39a5-b13c-426f-aa29-da82bb6dbf29');

INSERT INTO event_schema.event_members VALUES
                                    ('b90dde83-0ceb-4fa1-b174-6dbeceddbc34','APPROVED','company','email','Мария','Владимирова','Андреевна','81999999999','0','603b961f-a315-4c6e-9305-bea0ef60a98c'),
                                    ('57b4c653-9dfb-4372-a029-a7a030c27918','APPROVED','company_name','test@test.test','Василий','Пупкин','Васильевич','82999999999','0','603b961f-a315-4c6e-9305-bea0ef60a98c'),
                                    ('673fe76c-9624-46df-ac01-2c6a9ba7cb28','APPROVED','Военмех','test1@test.test','Милана','Фролова','Львовна','83999999999','0','603b961f-a315-4c6e-9305-bea0ef60a98c'),
                                    ('7f3795eb-06cf-4fea-a097-3cb2495c3853','APPROVED','Яндекс','test2@test.test','Дарья','Киселева','Григорьевна','84999999999','0','603b961f-a315-4c6e-9305-bea0ef60a98c'),
                                    ('d259817f-7a1a-4c36-a1b8-bcbd580944b8','APPROVED','СБЕР','test3@test.test','Марк','Смирнов','Кириллович','59999999999','0','603b961f-a315-4c6e-9305-bea0ef60a98c'),
                                    ('3e4bd62d-e3db-4b63-bfb3-5246ccdac251','APPROVED','РОСТЕХ','test4@test.test','Всеволод','Королев','Максимович','86999999999','0','603b961f-a315-4c6e-9305-bea0ef60a98c');