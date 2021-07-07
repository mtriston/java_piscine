DROP TABLE if EXISTS public.products CASCADE;

CREATE TABLE if NOT EXISTS public.products (
    id SERIAL PRIMARY KEY,
    name TEXT,
    price NUMERIC(10, 2)
);