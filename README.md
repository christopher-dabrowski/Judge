# Sędzia na Algorytmy i struktury danych

Program napisany w ramach zadania przewodniego drugiej połowy semestru laboratoriów z _Algorytmów i Struktur Danych_ prowadzonych przez dr Tomasza Lesia.

Celem zadania jest napisanie programu przeprowadzającego turniej w [grę logiczną](##Opis-gry) między podanymi graczami (napisanymi przez innych studentów). Program zajmuje się kolejkowaniem meczy, obróbką i prezentacją wyników i historii oraz pełni rolę arbitra między graczami w trakcie trwania rozgrywki.

## Opis gry
Gracze stawiają na przemian bloki **2x1** (przypominające kostki domino). Dany blok można postawić **pionowo** lub **poziomo** na wolnych polach planszy.  
**Celem gry** jest uniemożliwienie przeciwnikowi wykonania ruchu, poprzez zajęcie ostatniego wolnego pola umożliwiającego ruch.

* Gra rozgrywana jest na planszy mającej **n x n** pól. Gdzie **n** jest nieparzystą liczbą naturalną, która zostanie wybrana w dniu przeprowadzenia turnieju.  
* Skrajne pola planszy sąsiadują ze sobą (plansza przypomina sferę).
* Nie możliwe jest położenie bloku _na skos_. Wyklucza to między innymi postawienie jednego bloku na przeciwległych rogach planszy.
* Na początku rozgrywki **10%** losowo wybranych pól jest zajętych.

## Wzięcie udziału
By wziąć udział w turnieju należy przygotować **dwa pliki**:
* Plik programu, który będzie grał w turnieju
* Plik **info.txt** ([_składnia_](##Plik-info.txt))

Oba te pliki należy umieścić w folderze, którego nazwa to **numer albumu** uczestnika.
Przykładowe foldery graczy można znaleźć w katalogu [example_players](example_players).

## Plik info.txt
Plik ten zawiera metadane dotyczące uczestnika turnieju.

### Składnia
\<_alias_\>  
\<_Imię_\> \<_Nazwisko_\>  
\<_Komenda odpalająca program_\>

### Przykładowy plik info.txt
Henro200  
Henryk Domaniewski  
java -jar TheAmazingProgram.jar

Inne przykłady znajdują się w podkatalogach katalogu [example_players](example_players).  
[Jeden z nich](example_players/024680/info.txt).

## Komunikacja
Przed rozpoczęciem rozgrywki sędzia wysyła do każdego z graczy informację o rozmiarze planszy oraz listę początkowych przeszkód.  
Podczas rozgrywki sędzia na przemian wysyła do gracza informację o ruchu przeciwnika i czeka na ruch gracza.  
Wszystkie komunikaty przesyłane są przez **standardowe wejście/wyjście** programu uczestnika.  

Czas w jakim gracz musi wysłać odpowiedź to **0,5s**. W przypadku nie otrzymania odpowiedzi gracz przegrywa aktualną grę.  
Wszystkie wiadomości są wysyłane jako **zwykły tekst** z kodowaniem ASCII.

Wysyłanie tekstu przez programy grające musi odbywać się __jednym ciągiem__, przez komendę typu `println()`. Kluczowe jest wysłanie **znaku nowej linii** na końcu komunikatu.  
Wysyłanie kolejnych liczb po jednej, nawet jeśli wszystkie zmieszczą się w wyznaczonym terminie nawet, jeśli dodany zostanie na końcu znak nowej linii, uznane będzie za ruch niepoprawny.

### Przykład komunikacji
| Lp. | Gracz           | Sędzia                        | Komentarz                                                   |
|-----|-----------------|-------------------------------|-------------------------------------------------------------|
| 1   |                 | 15                            | Rozmiar planszy                                             |
| 2   | ok              |                               | Potwierdzenie                                               |
| 3   |                 | {0;1},{2;2},{2;3}             | Lista przeszkód                                             |
| 4   | ok              |                               | Potwierdzenie                                               |
| 5   |                 | start **LUB** {x1;y1},{x2;y2} | Komunikat rozpoczęcia **LUB** Pierwszy ruch drugiego gracza |
| 6   | {x1;y1},{x2;y2} |                               | Ruch gracza                                                 |


-----------------

**Autorzy:**  
Patryk Piętka  
Krzysztof Dąbrowski