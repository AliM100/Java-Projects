use master
go

create database foodDB
on
	(name=foodDB_dat,
	Filename='C:\database project\+data\foodDB.mdf')
log on
	(name='foodDB_log',
	Filename='C:\database project\+data\foodDB.ldf')
go