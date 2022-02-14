# LewisCurrencyList


1) DI (V)
2) Database (V)
3) Layers (V)
4) Models (V)
5) UI (V)
6) Sort
7) fast clicks handling

Todo list:

1) clean up
2) Tests (Unit/UI)

First time using room, I have used DBFlow about 4 years ago, but with banking apps, 
we weren't really allowed to used database to save any of the customer data
Multi threading -> UI blocking resolving fast double clock of sorting), coroutine
Data concern, why would you want to pass down the whole list from activity to fragment
We can have cache in memory and using a key to retrieve or have

Regarding layout xmls, there are different ways of constructing the same layout, but some will have slightly better performance
(list item can use linear/relative if possible as sometimes constraint layout within recyclerview can cause performance issue)
Also in the demo activity, the header actions and description can be part of either way, depending on how the designer/business wants the flow to be. 
