DROP TABLE IF EXISTS detalii_comanda;
DROP TABLE IF EXISTS comenzi;
DROP TABLE IF EXISTS produse;
DROP TABLE IF EXISTS distribuitori;
DROP TABLE IF EXISTS categorii;

CREATE TABLE categorii (
    id INTEGER PRIMARY KEY,
    nume TEXT NOT NULL,
    descriere TEXT,
    cod_categorie TEXT NOT NULL UNIQUE
);

CREATE TABLE distribuitori (
    id INTEGER PRIMARY KEY,
    nume TEXT NOT NULL,
    email TEXT,
    telefon TEXT,
    adresa TEXT,
    cui TEXT NOT NULL UNIQUE
);

CREATE TABLE produse (
    cod TEXT PRIMARY KEY,
    nume TEXT NOT NULL,
    descriere TEXT,
    pret REAL NOT NULL,
    cantitate INTEGER NOT NULL,
    stoc_minim INTEGER NOT NULL,
    categorie_id INTEGER NOT NULL,
    distribuitor_id INTEGER,
    FOREIGN KEY (categorie_id) REFERENCES categorii(id),
    FOREIGN KEY (distribuitor_id) REFERENCES distribuitori(id)
);

CREATE TABLE comenzi (
    id INTEGER PRIMARY KEY,
    distribuitor_id INTEGER NOT NULL,
    data_comanda TEXT NOT NULL,
    status TEXT NOT NULL,
    FOREIGN KEY (distribuitor_id) REFERENCES distribuitori(id)
);

CREATE TABLE detalii_comanda (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    comanda_id INTEGER NOT NULL,
    produs_cod TEXT NOT NULL,
    cantitate INTEGER NOT NULL,
    pret_unitar REAL NOT NULL,
    FOREIGN KEY (comanda_id) REFERENCES comenzi(id),
    FOREIGN KEY (produs_cod) REFERENCES produse(cod)
);