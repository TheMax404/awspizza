INSERT INTO piatto (id, nome, descrizione, prezzo, visibile)
VALUES
(1, 'Pizza Margherita', 'Fiordilatte, pomodoro, basilico', '12.50', TRUE),
(2, 'Pizza Quattro Stagioni', 'Prosciutto cotto, funghi, carciofi, olive nere', '14.00', TRUE),
(3, 'Pizza Diavola', 'Salsiccia piccante, pomodoro, mozzarella', '13.00', TRUE),
(4, 'Pizza Capricciosa', 'Prosciutto cotto, funghi, carciofi, olive verdi', '13.50', TRUE),
(5, 'Pizza Napoletana', 'Alici, capperi, pomodoro, origano', '12.50', TRUE),
(6, 'Pizza Marinara', 'Aglio, origano, pomodoro', '11.00', FALSE);

INSERT INTO ordine(id, stato, totale, note, data_inserimento, data_completamento)
VALUES
(NEXT VALUE FOR ordine_seq, 'COMPLETED', '125.00', 'Niente pomodoro', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(NEXT VALUE FOR ordine_seq, 'PENDING', '152.00', null, CURRENT_TIMESTAMP, null),
(NEXT VALUE FOR ordine_seq, 'PENDING', '12.50', null, CURRENT_TIMESTAMP, null);

INSERT INTO ordine_piatto (id_ordine, id_piatto)
VALUES
(1, 1),
(2,1),
(2,2),
(2,3),
(2,5),
(3,1);