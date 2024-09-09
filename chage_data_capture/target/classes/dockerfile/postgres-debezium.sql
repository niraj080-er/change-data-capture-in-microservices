-- Connect to your database
\c Customer

-- Create a replication slot
SELECT * FROM pg_create_logical_replication_slot('debezium_slot', 'pgoutput');

-- Create a publication
CREATE PUBLICATION debezium_publication FOR TABLE public.outbox;


SELECT * FROM pg_replication_slots;
SELECT * FROM pg_publication;

 