--TABLES
--DROP
drop table brandmodelmap;
drop table briefspecification;
drop table detailspecification;
drop table urlhtml;

--CREATE
CREATE TABLE brandmodelmap (
  `mapid` INT NOT NULL AUTO_INCREMENT,
  `brandname` text DEFAULT NULL,
  `brandurl` text DEFAULT NULL,
  `modelname` text DEFAULT NULL,
  `modelurl` text DEFAULT NULL,
  PRIMARY KEY (mapid)
);

CREATE TABLE briefspecification (
  `specificationid` INT NOT NULL AUTO_INCREMENT,
  `mapid` text NOT NULL,
  `devicename` text DEFAULT NULL,
  `dimensions` text DEFAULT NULL,
  `weight` text DEFAULT NULL,
  `soc` text DEFAULT NULL,
  `cpu` text DEFAULT NULL,
  `gpu` text DEFAULT NULL,
  `ram` text DEFAULT NULL,
  `storage` text DEFAULT NULL,
  `memorycards` text DEFAULT NULL,
  `display` text DEFAULT NULL,
  `battery` text DEFAULT NULL,
  `os` text DEFAULT NULL,
  `camera` text DEFAULT NULL,
  `simcards` text DEFAULT NULL,
  `wifi` text DEFAULT NULL,
  `usb` text DEFAULT NULL,
  `bluetooth` text DEFAULT NULL,
  `positioning` text DEFAULT NULL,
  PRIMARY KEY (specificationid)
);

CREATE TABLE detailspecification (
  `specificationid` INT NOT NULL AUTO_INCREMENT,
  `mapid` text NOT NULL,
  `category` text DEFAULT NULL,
  `categorydescription` text DEFAULT NULL,
  `propertyname` text DEFAULT NULL,
  `propertydescription` text DEFAULT NULL,
  `valuesarray` text NOT NULL,
  PRIMARY KEY (specificationid)
);

CREATE TABLE urlhtml (
  `id` INT NOT NULL AUTO_INCREMENT,
  `url` text NOT NULL,
  `html` text NOT NULL,
  PRIMARY KEY (id)
);


--VIEWS
--DROP
drop view invalid_brief_specifications;

--CREATE
create view invalid_brief_specifications as
select * from briefspecification where
dimensions is null or
soc is null or
cpu is null or
gpu is null or
ram is null or
storage is null or
display is null or
lower(soc) like '%intel%' or
lower(cpu) like '%intel%';

--SELECT
select * from brandmodelmap;
select * from briefspecification;
select * from detailspecification;
select * from urlhtml;
select * from invalid_brief_specifications;