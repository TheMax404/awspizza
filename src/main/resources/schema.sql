create table PIATTO(
    id int PRIMARY KEY,
    nome varchar(150) not null,
    descrizione varchar(500),
    prezzo decimal(10,2) not null,
    visibile boolean not null
);


create table ordine(
    id int PRIMARY KEY,
    stato varchar(25) not null,
    note varchar(255),
    totale decimal(10,2) not null,
    data_inserimento datetime not null,
    data_completamento datetime
);
--
CREATE TABLE ordine_piatto(
    id_ordine int,
    id_piatto int,
    CONSTRAINT FK_piatto FOREIGN KEY (id_piatto) REFERENCES piatto(id),
    CONSTRAINT FK_ordine FOREIGN KEY (id_ordine) REFERENCES ordine(id)
);
CREATE INDEX ordine_piatto_index ON ordine_piatto (id_ordine);

CREATE SEQUENCE ORDINE_SEQ
MINVALUE 1
MAXVALUE 99999
INCREMENT BY 1 ;