# Sędzia na Algorytmy i struktury danych

Program napisany w ramach zadania przewodniego drugiej połowy semestru laboratoriów z _Algorytmów i Struktur Danych_ prowadzonych przez dr Tomasza Lesia.


Celem zadania jest napisanie programu przeprowadzającego turniej w [grę logiczną](##Opis-gry) między podanymi graczami (napisanymi przez innych studentów). Program zajmuje się kolejkowaniem meczy, obróbką i prezentacją wyników i historii oraz pełni rolę arbitra między graczami w trakcie trwania rozgrywki.

## Przykład komunikacji
| Lp. | Gracz           | Sędzia                        | Komentarz                                                   |
|-----|-----------------|-------------------------------|-------------------------------------------------------------|
| 1   |                 | 15                            | Rozmiar planszy                                             |
| 2   | ok              |                               | Potwierdzenie                                               |
| 3   |                 | {0;1},{2;2},{2;3}             | Lista przeszkód                                             |
| 4   | ok              |                               | Potwierdzenie                                               |
| 5   |                 | start **LUB** {x1;y1},{x2;y2} | Komunikat rozpoczęcia **LUB** Pierwszy ruch drugiego gracza |
| 6   | {x1;y1},{x2;y2} |                               | Ruch gracza                                                 |

Wszystkie wiadomości są wysyłane jako **zwykły tekst**.

-----------------

**Autorzy:**  
Patryk Piętka  
Krzysztof Dąbrowski