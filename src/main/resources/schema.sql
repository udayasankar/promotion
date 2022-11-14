DROP TABLE IF EXISTS ITEMS;
DROP TABLE IF EXISTS ACTIVEPROMOTION;
CREATE TABLE ITEMS (
                      ITEM_ID TEXT PRIMARY KEY NOT NULL,
                      ITEM_PRICE decimal NOT NULL
);
CREATE TABLE ACTIVEPROMOTION (
                       ITEM_ID TEXT PRIMARY KEY,
                       PROMO_ITEM_ID TEXT NOT NULL,
                       PROMO_NO INT NOT NULL,
                       PROMO_ITEM_PRICE decimal NOT NULL,
                       PROMO_ITEM_IDS TEXT
);
