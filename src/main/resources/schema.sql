--Types

CREATE TYPE report_monthly_marketplace AS (
        month_num char(10),
        listing_count char(10),
        listing_sum_price char(10),
        listing_avg_price char(10)
);

CREATE TYPE report_monthly_email AS (
        month_num char(2),
        email char(1000)
);

--Tables

CREATE TABLE public.listing (
	id uuid NOT NULL,
	currency varchar(3) NOT NULL,
	description varchar(255) NOT NULL,
	listing_price numeric(2) NOT NULL,
	owner_email_address varchar(255) NOT NULL,
	quantity int4 NOT NULL,
	title varchar(255) NOT NULL,
	upload_time date NOT NULL,
	listing_status int8 NOT NULL,
	inventory_item_location_id uuid NOT NULL,
	marketplace int8 NOT NULL,
	CONSTRAINT listing_pkey PRIMARY KEY (id),
	CONSTRAINT fkew3a95ns0ixot7uqmhv0hcfj FOREIGN KEY (marketplace) REFERENCES marketplace(id),
	CONSTRAINT fkpb2p6amypcfr1xb946waf96h4 FOREIGN KEY (inventory_item_location_id) REFERENCES location(id),
	CONSTRAINT fktr4s9gxbwl34a7eydclnr91e3 FOREIGN KEY (listing_status) REFERENCES listing_status(id)
);

CREATE TABLE public.listing_status (
	id int8 NOT NULL,
	status_name varchar(255) NULL,
	CONSTRAINT listing_status_pkey null
);

CREATE TABLE public."location" (
	id uuid NOT NULL,
	address_primary varchar(255) NULL,
	address_secondary varchar(255) NULL,
	country varchar(255) NULL,
	manager_name varchar(255) NULL,
	phone varchar(255) NULL,
	postal_code varchar(255) NULL,
	town varchar(255) NULL,
	CONSTRAINT location_pkey null
);

CREATE TABLE public.marketplace (
	id int8 NOT NULL,
	marketplace_name varchar(255) NULL,
	CONSTRAINT marketplace_pkey null
);
