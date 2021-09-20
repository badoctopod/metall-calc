(ns metall-calc.config
  (:require
   [reagent.core :as r]))

(defonce global-state (r/atom {}))

(def products [{:id 1 :code "angle" :name "Уголок" :dimension [{:id 1 :code "height" :name "Высота, мм"}
                                                               {:id 2 :code "width" :name "Ширина, мм"}
                                                               {:id 3 :code "wall-thickness" :name "Толщина стенки, мм"}
                                                               {:id 4 :code "length" :name "Длина, мм"}
                                                               {:id 5 :code "qty" :name "Количество, шт"}]}
               {:id 2 :code "sheet" :name "Лист" :dimension [{:id 1 :code "sheet-thickness" :name "Толщина листа, мм"}
                                                             {:id 2 :code "width" :name "Ширина, мм"}
                                                             {:id 3 :code "length" :name "Длина, мм"}
                                                             {:id 4 :code "qty" :name "Количество, шт"}]}
               {:id 3 :code "profile" :name "Труба (профильная)" :dimension [{:id 1 :code "height" :name "Высота, мм"}
                                                                             {:id 2 :code "width" :name "Ширина, мм"}
                                                                             {:id 3 :code "wall-thickness" :name "Толщина стенки, мм"}
                                                                             {:id 4 :code "length" :name "Длина, мм"}
                                                                             {:id 5 :code "qty" :name "Количество, шт"}]}
               {:id 4 :code "pipe" :name "Труба (круглая)" :dimension [{:id 1 :code "diameter" :name "Диаметр, мм"}
                                                                       {:id 2 :code "wall-thickness" :name "Толщина стенки, мм"}
                                                                       {:id 3 :code "length" :name "Длина, мм"}
                                                                       {:id 4 :code "qty" :name "Количество, шт"}]}
               {:id 5 :code "circle" :name "Круг (проволока)" :dimension [{:id 1 :code "diameter" :name "Диаметр, кв.мм"}
                                                                          {:id 2 :code "length" :name "Длина, мм"}
                                                                          {:id 3 :code "qty" :name "Количество, шт"}]}
               {:id 6 :code "square" :name "Квадрат" :dimension [{:id 1 :code "width" :name "Ширина, мм"}
                                                                 {:id 2 :code "length" :name "Длина, мм"}
                                                                 {:id 3 :code "qty" :name "Количество, шт"}]}
               {:id 7 :code "channel" :name "Швеллер" :dimension [{:id 1 :code "height" :name "Высота, мм"}
                                                                  {:id 2 :code "width" :name "Ширина, мм"}
                                                                  {:id 3 :code "wall-thickness" :name "Толщина стенки, мм"}
                                                                  {:id 4 :code "length" :name "Длина, мм"}
                                                                  {:id 5 :code "qty" :name "Количество, шт"}]}
               {:id 8 :code "ibeam" :name "Балка (двутавр)" :dimension [{:id 1 :code "height" :name "Высота, мм"}
                                                                        {:id 2 :code "width" :name "Ширина, мм"}
                                                                        {:id 3 :code "shelf-thickness" :name "Толщина полок, мм"}
                                                                        {:id 4 :code "bulkhead-thickness" :name "Толщина перемычки, мм"}
                                                                        {:id 5 :code "length" :name "Длина, мм"}
                                                                        {:id 6 :code "qty" :name "Количество, шт"}]}
               {:id 9 :code "tape" :name "Лента (полоса)" :dimension [{:id 1 :code "width" :name "Ширина, мм"}
                                                                      {:id 2 :code "tape-thickness" :name "Толщина ленты, мм"}
                                                                      {:id 3 :code "length" :name "Длина, мм"}
                                                                      {:id 4 :code "qty" :name "Количество, шт"}]}
               {:id 10 :code "hex" :name "Шестигранник" :dimension [{:id 1 :code "diameter" :name "Диаметр, кв.мм"}
                                                                    {:id 2 :code "length" :name "Длина, мм"}
                                                                    {:id 3 :code "qty" :name "Количество, шт"}]}])

(def metals [{:id 1 :code "mebl"    :name "Металлы черные"}
             {:id 2 :code "al"      :name "Алюминий и сплавы алюминиевые"}
             {:id 3 :code "babbits" :name "Баббиты оловянные и свинцовые"}
             {:id 4 :code "mg"      :name "Магний и сплавы магниевые"}
             {:id 5 :code "cu"      :name "Медь и медные сплавы"}
             {:id 6 :code "ti"      :name "Титан и титановые сплавы"}
             {:id 7 :code "me"      :name "Прочие металлы"}])

(def brands [{:id 1  :brand "Сталь 10 ГОСТ 1050-88" :density 7856 :code "mebl"}
             {:id 2  :brand "Сталь 20 ГОСТ 1050-88" :density 7859 :code "mebl"}
             {:id 3  :brand "Сталь 40 ГОСТ 1050-88" :density 7850 :code "mebl"}
             {:id 4  :brand "Сталь 60 ГОСТ 1050-88" :density 7800 :code "mebl"}
             {:id 5  :brand "С235-С375 ГОСТ 27772-88" :density 7850 :code "mebl"}
             {:id 6  :brand "Ст3пс ГОСТ 380-2005" :density 7850 :code "mebl"}
             {:id 7  :brand "Чугун ковкий КЧ 70-2 ГОСТ 1215-79" :density 7200 :code "mebl"}
             {:id 8  :brand "Чугун высокопрочный ВЧ35 ГОСТ 7293-85" :density 7200 :code "mebl"}
             {:id 9  :brand "Чугун серый СЧ10 ГОСТ 1412-85" :density 6800 :code "mebl"}
             {:id 10 :brand "Чугун серый СЧ20 ГОСТ 1412-85" :density 7100 :code "mebl"}
             {:id 11 :brand "Чугун серый СЧ30 ГОСТ 1412-85" :density 7300 :code "mebl"}
             {:id 12 :brand "Силумин АК12ж ГОСТ 1583-93" :density 2700 :code "al"}
             {:id 13 :brand "Сплав АК12 ГОСТ 1583-93" :density 2710 :code "al"}
             {:id 14 :brand "Сплав АК5М ГОСТ 1583-93" :density 2640 :code "al"}
             {:id 15 :brand "Сплав АК7 ГОСТ 1583-93" :density 2700 :code "al"}
             {:id 16 :brand "Сплав АО9-1 ГОСТ 14113-78" :density 2700 :code "al"}
             {:id 17 :brand "Б83 ГОСТ 1320-74" :density 7380 :code "babbits"}
             {:id 18 :brand "Б87 ГОСТ 1320-74" :density 7300 :code "babbits"}
             {:id 19 :brand "БН ГОСТ 1320-74" :density 9550 :code "babbits"}
             {:id 20 :brand "Сплав МЛ10...МЛ19 ГОСТ 2856-79" :density 1810 :code "mg"}
             {:id 21 :brand "Сплав ВМЛ5" :density 1890 :code "mg"}
             {:id 22 :brand "Сплав ВМЛ9" :density 1850 :code "mg"}
             {:id 23 :brand "Бронза оловянная БрО10C10" :density 8800 :code "cu"}
             {:id 24 :brand "Бронза оловянная БрО19" :density 8600 :code "cu"}
             {:id 25 :brand "Бронза оловянная БрОC10-10" :density 9100 :code "cu"}
             {:id 26 :brand "Бронза оловянная БрОA10-1" :density 8750 :code "cu"}
             {:id 27 :brand "Бронза БрА10Ж3Мч2 ГОСТ 493-79" :density 8200 :code "cu"}
             {:id 28 :brand "Бронза БрА9Ж3Л ГОСТ 493-79" :density 8200 :code "cu"}
             {:id 29 :brand "Бронза БрМц5 ГОСТ 18175-78" :density 8600 :code "cu"}
             {:id 30 :brand "Латунь Л60 ГОСТ 15527-2004" :density 8800 :code "cu"}
             {:id 31 :brand "Латунь ЛА ГОСТ 1020-97" :density 8500 :code "cu"}
             {:id 32 :brand "Медь М0, М1, М2, М3 ГОСТ 859-2001" :density 8940 :code "cu"}
             {:id 33 :brand "Медь МСр1 ГОСТ 16130-90" :density 8900 :code "cu"}
             {:id 34 :brand "ВТ1-0 ГОСТ 19807-91" :density 4500 :code "ti"}
             {:id 35 :brand "ВТ14 ГОСТ 19807-91" :density 4500 :code "ti"}
             {:id 36 :brand "ВТ20Л ГОСТ 19807-91" :density 4470 :code "ti"}
             {:id 37 :brand "Вольфрам ВА ГОСТ 18903-73" :density 19300 :code "me"}
             {:id 38 :brand "Вольфрам ВТ-7 ГОСТ 18903-73" :density 19300 :code "me"}
             {:id 39 :brand "Золото Зл 99,9 ГОСТ 6835-2002" :density 19300 :code "me"}
             {:id 40 :brand "Индий ИНО ГОСТ 10297-94" :density 7300 :code "me"}
             {:id 41 :brand "Кадмий КдО ГОСТ 1467-93" :density 8640 :code "me"}
             {:id 42 :brand "Олово О1пч ГОСТ 860-75" :density 7300 :code "me"}
             {:id 43 :brand "Паладий Пд 99,8 ГОСТ 13462-79" :density 12160 :code "me"}
             {:id 44 :brand "Платина Пд 99,8 ГОСТ 13498-79" :density 21450 :code "me"}
             {:id 45 :brand "Свинец С0 ГОСТ 3778-98" :density 11400 :code "me"}
             {:id 46 :brand "Серебро 99,9 ГОСТ 6836-2002" :density 11500 :code "me"}
             {:id 47 :brand "Цинк Ц1 ГОСТ 3640-94" :density 7130 :code "me"}])
