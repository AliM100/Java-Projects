/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2017                    */
/* Created on:     01/09/2021 1:45:30 PM                        */
/*==============================================================*/
use foodDB
go

/*==============================================================*/
/* Table: CLIENT                                                */
/*==============================================================*/
create table CLIENT (
   CID                  int                  identity(1,1),
   CNAME                varchar(50)          null,
   CPASS                varchar(50)          null,
   constraint PK_CLIENT primary key (CID)
)
go

/*==============================================================*/
/* Table: CREDITCARD                                            */
/*==============================================================*/
create table CREDITCARD (
   CCNB                 int                  not null,
   CID                  int                  not null,
   CCTYPE               varchar(50)          null,
   constraint PK_CREDITCARD primary key (CCNB)
)
go

/*==============================================================*/
/* Table: MANAGER                                               */
/*==============================================================*/
create table MANAGER (
   MID                  int                  identity(1,1),
   MNAME                varchar(50)          null,
   MPASS                varchar(50)          null,
   constraint PK_MANAGER primary key (MID)
)
go

/*==============================================================*/
/* Table: "ORDER"                                               */
/*==============================================================*/
create table "ORDER" (
   OID                  int                  identity(1,1),
   CID                  int                  not null,
   OTOTALPRICE          float                null,
   OLOCATION            varchar(100)         null,
   TELEPHONENB          int                  null,
   
   constraint PK_ORDER primary key (OID)
)
go

/*==============================================================*/
/* Table: ORDERITEMS                                            */
/*==============================================================*/
create table ORDERITEMS (
   PID                  int                  not null,
   OID                  int                  not null,
   QUANTITY             int                  null,
   constraint PK_ORDERITEMS primary key (PID, OID)
)
go

/*==============================================================*/
/* Table: PRODUCT                                               */
/*==============================================================*/
create table PRODUCT (
   PID                  int                  identity(1,1),
   PTYPE                varchar(50)          null,
   PNAME                varchar(50)          null,
   PPRICE               float                null,
   PIMAGE               varchar(200)         null,
   constraint PK_PRODUCT primary key (PID)
)
go

 /*modified*/
create table TABLES (
   TID                  int                  identity(1,1),
   TTYPE                varchar(50)          null,
   TCAP                varchar(50)          null,
   TISRESERVED          int                null,
   TTIME				DateTime			null,
   constraint PK_PRODUCT primary key (TID)
)
go


