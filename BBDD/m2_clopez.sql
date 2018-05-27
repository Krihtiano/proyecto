--
-- Estructura de la taula soci

CREATE TABLE IF NOT EXISTS soci (
  id int(11) NOT NULL AUTO_INCREMENT,
  nif varchar(9) NOT NULL,
  nom varchar(30) NOT NULL,
  cognom1 varchar(30) NOT NULL,
  cognom2 varchar(30) NOT NULL,
  data_alta timestamp NOT NULL,
  password_hash varchar(32) NOT NULL,
  foto longblob,
  actiu int(1) NOT NULL,
  PRIMARY KEY (id)
);

--
-- Estructura de la taula modalitat
CREATE TABLE IF NOT EXISTS modalitat (
  id int(11) NOT NULL AUTO_INCREMENT,
  description varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

--
-- Estructura de la taula estadistica_modalitat
CREATE TABLE IF NOT EXISTS estadistica_modalitat (
  soci_id int(11) NOT NULL,
  modalitat_id int(11) NOT NULL,
  coeficient_base float NOT NULL,
  total_caramboles_temporada_actual int(11) NOT NULL,
  total_entrades_temporada_actual int(11) NOT NULL,
  PRIMARY KEY (soci_id,modalitat_id),
  CONSTRAINT estadistica_modalitat_modalitat_id_fk FOREIGN KEY (modalitat_id) REFERENCES modalitat (id),
  CONSTRAINT estadistica_modalitat_soci_id_fk FOREIGN KEY (soci_id) REFERENCES soci (id)
);

--
-- Estructura de la taula torneig
CREATE TABLE IF NOT EXISTS torneig (
  id int(11) NOT NULL AUTO_INCREMENT,
  nom varchar(30) NOT NULL,
  data_inici timestamp NOT NULL,
  data_finalitzacio timestamp,
  preinscripcio_oberta int(1) NOT NULL,
  grups_creats int(1) NOT NULL,
  modalitat_id int(11) NOT NULL,
  CONSTRAINT torneig_modalitat_id_fk FOREIGN KEY (modalitat_id) REFERENCES modalitat (id),
  PRIMARY KEY (id)
);

--
-- Estructura de la taula grup
CREATE TABLE IF NOT EXISTS grup (
  num int(11) NOT NULL,
  description varchar(60) NOT NULL,
  caramboles_victoria int(11) NOT NULL,
  limit_entrades int(11) NOT NULL,
  torneig_id int(11) NOT NULL,
  PRIMARY KEY (torneig_id, num),
  CONSTRAINT grup_torneig_id_fk FOREIGN KEY (torneig_id) REFERENCES torneig (id)
);

--
-- Estructura de la taula inscrit
CREATE TABLE IF NOT EXISTS inscrit (
  soci_id int(11) NOT NULL,
  torneig_id int(11) NOT NULL,
  grup_num int(11),
  data timestamp NOT NULL,
  PRIMARY KEY (soci_id,torneig_id),
  CONSTRAINT inscrit_soci_id_fk FOREIGN KEY (soci_id) REFERENCES soci (id),
  CONSTRAINT inscrit_torneig_id_fk FOREIGN KEY (torneig_id) REFERENCES grup (torneig_id),
  CONSTRAINT inscrit_grup_num_fk FOREIGN KEY (torneig_id,grup_num) REFERENCES grup (torneig_id, num)
);

--
-- Estructura de la taula partida
CREATE TABLE IF NOT EXISTS partida (
  id int(11) NOT NULL AUTO_INCREMENT,
  caramboles_a int(11) NOT NULL,
  caramboles_b int(11) NOT NULL,
  data timestamp,
  num_entrades int(11) NOT NULL,
  taula_id int(11),
  torneig_id int(11) NOT NULL,
  grup_num int(11) NOT NULL,
  inscrit_a int(11) NOT NULL,
  inscrit_b int(11) NOT NULL,
  motiu_victoria varchar(20),
  guanyador char(1),
  estat_partida varchar(10) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT partida_grup_num_fk FOREIGN KEY (torneig_id,grup_num) REFERENCES grup (torneig_id, num),
  CONSTRAINT partida_inscrit_id_fk_A FOREIGN KEY (inscrit_a, torneig_id) REFERENCES inscrit (soci_id, torneig_id),
  CONSTRAINT partida_inscrit_id_fk_B FOREIGN KEY (inscrit_b, torneig_id) REFERENCES inscrit (soci_id, torneig_id)
  
);

INSERT INTO modalitat (description) VALUES ('Lliure');
INSERT INTO modalitat (description) VALUES ('A 1 banda');
INSERT INTO modalitat (description) VALUES ('A 3 bandes');


