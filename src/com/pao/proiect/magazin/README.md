# Gestiune Stocuri Magazin

Tema aleasa: **Gestiune stocuri magazin** — categorii, produse, distribuitori, comenzi.

---

## Tipuri de obiecte

- `Categorie` — grupare logica a produselor (ex: Electronice, Alimente)
- `Produs` — articol din stoc, cu pret, cantitate si stoc minim configurat
- `CodProdus` — identificator unic al unui produs, generat automat dupa categorie si an; clasa imutabila
- `Distribuitor` — compania de la care magazinul se aprovizioneaza
- `Comanda` — comanda plasata catre un distribuitor, cu status si lista de produse
- `DetaliiComanda` — o linie dintr-o comanda: ce produs, cata cantitate, la ce pret unitar
- `StocAlert` — alerta generata automat cand un produs scade sub stocul minim
- `Angajat` — persoana angajata in magazin, cu departament si salariu
- `Manager` — tip special de angajat care gestioneaza o echipa (extinde `Angajat`)

Ierarhia de mostenire: `Persoana` (abstracta) → `Angajat` → `Manager`

---

## Actiuni posibile in sistem

1. **Adauga un produs nou** — se specifica categoria, pretul, cantitatea initiala si stocul minim; se genereaza automat un cod unic de forma `EL-2024-0001`
2. **Actualizeaza stocul unui produs** — dupa receptia unei livrari, cantitatea se modifica direct pe produs
3. **Plaseaza o comanda catre un distribuitor** — se selecteaza distribuitorul si se adauga mai multe produse cu cantitati; se calculeaza totalul automat
4. **Cauta produse dupa categorie** — returneaza toate produsele dintr-o categorie data, folosind indexarea din `Map<String, List<Produs>>`
5. **Listeaza produsele cu stoc sub minim** — util pentru a sti ce trebuie reaprovizionat urgent
6. **Afiseaza istoricul comenzilor unui distribuitor** — toate comenzile plasate catre un anumit furnizor, cu detalii si total
7. **Sterge un produs din sistem** — il elimina din lista generala si din indexul pe categorii
8. **Top produse dupa valoarea stocului** — sorteaza produsele descrescator dupa `pret * cantitate`; foloseste `Comparable` implementat pe `Produs`
9. **Cauta un distribuitor dupa nume** — cautare case-insensitive in lista de distribuitori
10. **Genereaza alerte de stoc critic** — parcurge toate produsele si creeaza obiecte `StocAlert` pentru cele cu stoc sub minim, cu timestamp