use master
go
execute sp_configure 'show advanced options',1;
go
RECONFIGURE
go
execute sp_configure 'xp_cmdshell',1;
go
RECONFIGURE
go
EXEC xp_cmdshell 'MD C:\database_for_foodDB'
create database foodDB
on
	(name=foodDB_dat,
	Filename='C:\database_for_foodDB\foodDB.mdf')
log on
	(name='foodDB_log',
	Filename='C:\database_for_foodDB\foodDB.ldf')
go

use foodDB
go
Drop table CLIENT
Drop table MANAGER
Drop table CREDITCARD
Drop table ORDER
Drop table ORDERITEMS
Drop table PRODUCT
Drop table DELIVERYINFO
Drop table Tabel
create table CLIENT (
   CID                  int                  identity(1,1),
   CNAME                varchar(50)          null,
   CPASS                varchar(500)         null,
   EMAIL                varchar(100)         null,
   constraint PK_CLIENT primary key (CID)
)
go

create table CREDITCARD (
   CCNB                 int                  not null,
   CID                  int                  not null,
   CCTYPE               varchar(50)          null,
   constraint PK_CREDITCARD primary key (CCNB)
)
go

create table MANAGER (
   MID                  int                  identity(1,1),
   MNAME                varchar(50)          null,
   MPASS                varchar(500)          null,
   EMAIL                varchar(100)         null,
   isMaster             int                  not null,
   constraint PK_MANAGER primary key (MID)
)
go

create table ORDERS(
   OID                  int                  identity(1,1),
   CID                  int                  not null,
   DID                  int                  not null,
   OTOTALPRICE          float                null,
   constraint PK_ORDER primary key (OID)
)
go

create table ORDERITEMS (
   PID                  int                  not null,
   OID                  int                  not null,
   QUANTITY             int                  null,
   constraint PK_ORDERITEMS primary key (PID, OID)
)
go

create table PRODUCT (
   PID                  int                  identity(1,1),
   PTYPE                varchar(50)          null,
   PNAME                varchar(50)          null,
   PPRICE               float                null,
   PIMAGE               varchar(200)         null,
   constraint PK_PRODUCT primary key (PID)
)
go

create table DELIVERYINFO (
   Did					int					 identity(1,1),	
   location             varchar(100)         null,
   telephoneNb          int                  null,
   constraint PK_DELIVERYINFO primary key (DID)
)
go
create table TABLES (
   TID                  int                  identity(1,1),
   TTYPE                varchar(50)          null,
   TCAP                varchar(50)          null,
   TISRESERVED          int                null,
   TTIME				DateTime			null,
   constraint PK_PRODUCT primary key (TID)
)
go

--select client by name and pass
CREATE PROCEDURE SelectClient @name nvarchar(50),@pass varchar(500)
AS
SELECT * FROM Client where Cname=@name and Cpass=HASHBYTES('SHA1',@pass)
GO
--select manager by name and pass
CREATE PROCEDURE SelectManager @name nvarchar(50),@pass varchar(500)
AS
SELECT * FROM Manager where Mname=@name and Mpass=HASHBYTES('SHA1',@pass)
GO
--print
CREATE PROCEDURE Printing @name nvarchar(50)
AS
print @name
GO
--modify totalprice after insertion of product in cart
CREATE PROCEDURE insertTp @pid int,@quan int,@oid int
AS
set nocount on
	declare @tp float,@price float
	set @tp=(SELECT Ototalprice from Orders where oid=@oid)
	set @price=(SELECT pprice from product where pid=@pid)
	update Orders set Ototalprice=((@price*@quan)+@tp) where oid=@oid
GO
--modify totalprice after deletion of product in cart
CREATE PROCEDURE deleteTp @pid int,@oid int
AS
set nocount on
	declare @tp float,@quan int,@price float
	set @tp=(SELECT Ototalprice from Orders where oid=@oid)
	set @quan=(SELECT quantity from OrderItems where oid=@oid and pid=@pid)
	set @price=(SELECT pprice from product where pid=@pid)
	update Orders set Ototalprice=(@tp-(@price*@quan)) where oid=@oid
GO
--delete product from cart
create procedure deletePfromC @prid int, @orid int
as
set nocount on
	exec deleteTp @pid=@prid,@oid=@orid
	delete from orderitems where pid=@prid and oid=@orid
	print 'product deleted from cart'
go
--update product in cart
create procedure updateCart @prid int, @orid int,@quantity int
as
set nocount on
	declare @oldquant int,@q int
	set @oldquant=(select quantity from ORDERITEMS where oid=@orid and pid=@prid)
	set @q=@quantity+@oldquant
	update ORDERITEMS set QUANTITY=@q where pid=@prid and oid=@orid
	exec insertTp @pid=@prid,@quan=@quantity,@oid=@orid
go
--delete product and if exist in orderitems also
create procedure deleteP @productId int
as
set nocount on
DECLARE mycursor CURSOR 
	FOR 
		select oid from orderitems where pid=@productId
	OPEN mycursor
	declare @orderid int
	FETCH NEXT FROM mycursor INTO @orderid
	WHILE @@FETCH_STATUS = 0 
	BEGIN 
		exec deletePfromC @prid=@productId,@orid=@orderid
	FETCH NEXT FROM mycursor  INTO @orderid
	END 
CLOSE mycursor 
DEALLOCATE mycursor
delete from product where pid=@productId
go
--print all customers with total count
create procedure printAllCustomers
as
	DECLARE mycursor CURSOR 
	FOR 
		select cname from client
	OPEN mycursor
	DECLARE @name varchar(50),@count int,@list varchar(3000)
	set @count=0
	set @list=''
	FETCH NEXT FROM mycursor  INTO @name
	WHILE @@FETCH_STATUS = 0 
	BEGIN 
		set @count=@count+1
		set @list=@list+@name+'---'
	FETCH NEXT FROM mycursor  INTO @name
	END 
	print @list+'Total Customers: '+CAST(@count AS VARCHAR)
	CLOSE mycursor 
	DEALLOCATE mycursor
	go
go
--print all managers with total count
create procedure printAllProducts
as
	DECLARE mycursor CURSOR 
	FOR 
		select pname from product
	OPEN mycursor
	DECLARE @name varchar(50),@count int,@list varchar(3000)
	set @count=0
	set @list=''
	FETCH NEXT FROM mycursor  INTO @name
	WHILE @@FETCH_STATUS = 0 
	BEGIN 
		set @count=@count+1
		set @list=@list+@name+'---'
	FETCH NEXT FROM mycursor  INTO @name
	END 
	print @list+'Total Products: '+CAST(@count AS VARCHAR)
	CLOSE mycursor 
	DEALLOCATE mycursor
	go
go
--delete customer with all related data
CREATE PROCEDURE deleteCus @name nvarchar(50)
AS
set nocount on
declare @cid int,@oid int
set @cid=(select cid from CLIENT where cname=@name)
set @oid=(select oid from orders where cid=@cid)
delete from CreditCard where cid=@cid
delete from DELIVERYINFO where did=(select did from orders where oid=@oid)
delete from ORDERITEMS where oid=@oid
delete from ORDERS where oid=@oid
delete from CLIENT where cid=@cid
print 'client deletion completed'
GO
--create customer and giving him order and deliveryinfo
CREATE PROCEDURE createCus @name nvarchar(50),@pass nvarchar(500),@email nvarchar(100)
AS
set nocount on
insert into client values(@name,@pass,@email)
declare @cid int,@deliveryID int
declare @DID table (DID int)
set @cid=(select cid from CLIENT where cname=@name)
INSERT INTO Deliveryinfo (telephonenb,location) output inserted.did into @did VALUES (null,null)
set @deliveryID=(select * from @DID)
insert into orders values(@cid,@deliveryID,0)
GO

CREATE FUNCTION getcid (@name varchar(50))
returns int
begin
	declare @cid int
	set @cid=(select cid from client where cname=@name)
	if(@cid=null) 
	begin
		exec Printing 'no such client'
		return 0
	end
	return @cid
end
go

CREATE FUNCTION getpid (@name varchar(50))
returns int
begin
	declare @pid int
	set @pid=(select pid from product where pname=@name)
	if(@pid=null) 
	begin
		exec Printing 'no such product'
		return 0
	end
	return @pid
end
go


CREATE FUNCTION getoid (@cid int)
returns int
begin
	declare @oid int
	set @oid=(select oid from orders where cid=@cid)
	if(@oid=null) 
	begin
		return 0
	end
	return @oid
end
go

CREATE FUNCTION getdid (@cid int)
returns int
begin
	declare @did int
	set @did=(select did from orders where cid=@cid)
	if(@did=null) 
	begin
		return 0
	end
	return @did
end
go

Create table trace(
loginname char(20) not null,
username char(20) not null,
tablename char(15) null,
operationtype char(15) null,
dtime datetime
)
go
--for gui
Create table totalorders(
Cid int,
TotalOrders int
)
go
create trigger TI_TOTALORDERS on totalorders instead of insert as
begin
set NOCOUNT ON
	if exists ( select T.Cid from totalorders T,inserted i where i.cid=T.cid)
	begin
		declare @TO int,@cid int
		set @TO=(select TotalOrders from inserted)
		set @CID=(select cid from inserted)
		update totalorders set TotalOrders=@TO+1 where cid=@cid
		return
	end
	insert into totalorders values((select cid from inserted),1)
    return
end
go

create trigger TI_CREDITCARD on CREDITCARD for insert as
begin
set nocount on
   declare
      @errno    int,
      @errmsg   varchar(255)
	if not exists(select * from client ,inserted where client.cid=inserted.cid)
	begin
		select @errno  = 50002,@errmsg = 'no such client id, cant insert.'
        goto error
	end
	print 'credit card insertion complete'
	return
/*  Errors handling  */
error:
    exec sp_addmessage @errno,16,@errmsg
    raiserror (@errno,16,1)
    rollback  transaction
	insert into trace values (SYSTEM_USER,USER,'creditcard','insert',GETDATE())
end
go


create trigger TU_CREDITCARD on CREDITCARD for update as
begin
set nocount on
   declare
      @errno    int,
      @errmsg   varchar(255)
	if not exists(select * from client ,inserted where client.cid=inserted.cid)
	begin
		select @errno  = 50003,@errmsg = 'no such client id, cant modify.'
        goto error
	end
	print 'credit card updated'
	return
	
/*  Errors handling  */
error:
    exec sp_addmessage @errno,16,@errmsg
    raiserror (@errno,16,1)
    rollback  transaction
	insert into trace values (SYSTEM_USER,USER,'creditcard','update',GETDATE())
end
go

create trigger TI_ORDERS on ORDERS for insert as
begin
set nocount on
    declare
       @errno    int,
       @errmsg   varchar(255)

    if not exists(select * from client ,inserted where client.cid=inserted.cid)
	begin
		select @errno  = 50002,@errmsg = 'no such client id, cant insert.'
        goto error
	end
	if not exists(select * from DELIVERYINFO ,inserted where DELIVERYINFO.did=inserted.did)
	begin
		select @errno  = 50002,@errmsg = 'no such deliveryinfo id, cant insert.'
        goto error
	end
    return
	
/*  Errors handling  */
error:
    exec sp_addmessage @errno,16,@errmsg
    raiserror (@errno,16,1)
    rollback  transaction
	insert into trace values(SYSTEM_USER,USER,'orders','insert',GETDATE());
end
go


create trigger TU_ORDERS on ORDERS for update as
begin
set nocount on
  declare
      @errno    int,
      @errmsg   varchar(255)

    if not exists(select * from client ,inserted where client.cid=inserted.cid)
	begin
		select @errno  = 50003,@errmsg = 'no such client id, cant modify.'
        goto error
	end
	if not exists(select * from DELIVERYINFO ,inserted where DELIVERYINFO.did=inserted.did)
	begin
		select @errno  = 50003,@errmsg = 'no such deliveryinfo id, cant modify.'
        goto error
	end
    return
	
/*  Errors handling  */
error:
    exec sp_addmessage @errno,16,@errmsg
    raiserror (@errno,16,1)
    rollback  transaction
	insert into trace values(SYSTEM_USER,USER,'orders','update',GETDATE());
end
go

create trigger TI_ORDERITEMS on ORDERITEMS instead of insert as
begin
set nocount on
    declare
       @errno    int,
       @errmsg   varchar(255)
	if not exists(select * from orders ,inserted where orders.oid=inserted.oid)
	begin
		select @errno  = 50002,@errmsg = 'no such order id, cant insert.'
        goto error
	end
	if not exists(select * from PRODUCT ,inserted where PRODUCT.pid=inserted.pid)
	begin
		select @errno  = 50002,@errmsg = 'no such product id, cant insert.'
        goto error
	end
	declare @productid int,@orderid int
	set @productid=(select pid from inserted)
	set @orderid=(select oid from inserted)
	declare @quant int
	set @quant=(select quantity from inserted)
	insert into ORDERITEMS values(@productid,@orderid,@quant)
	exec insertTp @pid=@productid,@quan=@quant,@oid=@orderid
	print 'orderitem insertion complete'
	return
/*  Errors handling  */
error:
    exec sp_addmessage @errno,16,@errmsg
    raiserror (@errno,16,1)
    rollback  transaction
	insert into trace values(SYSTEM_USER,USER,'orderitems','insert',GETDATE());
end
go

create trigger TU_ORDERITEMS on ORDERITEMS for update as
begin
set nocount on
   declare
      @errno    int,
      @errmsg   varchar(255)

    if not exists(select * from orders ,inserted where orders.oid=inserted.oid)
	begin
		select @errno  = 50003,@errmsg = 'no such order id, cant modify.'
        goto error
	end
	if not exists(select * from PRODUCT ,inserted where PRODUCT.pid=inserted.pid)
	begin
		select @errno  = 50003,@errmsg = 'no such product id, cant modify.'
        goto error
	end
	print 'orderitem updated'
    return

/*  Errors handling  */
error:
    exec sp_addmessage @errno,16,@errmsg
    raiserror (@errno,16,1)
    rollback  transaction
	insert into trace values(SYSTEM_USER,USER,'orderitems','update',GETDATE());
end
go

create trigger TI_Manager on Manager instead of insert as
begin
set nocount on
    declare
       @errno    int,
       @errmsg   varchar(255)

		if exists ( select * from Client C,inserted i where i.Mname=C.Cname)
		begin
			select @errno  = 50002,
                    @errmsg = 'username exist choose another.'
             goto error
		end
	if exists ( select * from Manager M,inserted i where i.Mname=M.Mname)
		begin
			select @errno  = 50002,
                    @errmsg = 'username exist choose another.'
             goto error
		end
	if exists ( select * from Client C,inserted i where i.email=C.email)
		begin
			select @errno  = 50002,
                    @errmsg = 'email exist choose another.'
             goto error
		end
	if exists ( select * from inserted i,Manager M where i.EMAIL=M.EMAIL)
		begin
			select @errno  = 50002,
                    @errmsg = 'email exist choose another.'
             goto error
		end
	if exists ( select * from Manager M,inserted i where i.isMaster=M.isMaster and M.isMaster=1)
		begin
			select @errno  = 50002,
                    @errmsg = 'only one master can be created.'
             goto error
		end
	declare @name varchar(50),@pass varchar(500),@email varchar(100),@isM int
	set @name=(select Mname from inserted)
	set @pass=(select Mpass from inserted)
	set @email=(select email from inserted)
	set @isM=(select isMaster from inserted)
	insert into Manager values(@name,HASHBYTES('SHA1',@pass),@email,@isM)
	print 'Manager insertion complete'
    return

/*  Errors handling  */
error:
	exec sp_addmessage @errno,16,@errmsg
    raiserror (@errno,16,1)
    rollback  transaction
	insert into trace values (SYSTEM_USER,USER,'Manager','insert',GETDATE())
end
go

create trigger TI_Client on Client instead of insert as
begin
set nocount on
    declare
       @errno    int,
       @errmsg   varchar(255)

	if exists ( select * from Client C,inserted i where i.Cname=C.Cname)
		begin
			select @errno  = 50002,
                    @errmsg = 'username exist choose another.'
             goto error
		end
	if exists ( select * from Manager M,inserted i where i.Cname=M.Mname)
		begin
			select @errno  = 50002,
                    @errmsg = 'username exist choose another.'
             goto error
		end
	if exists ( select * from Client C,inserted i where i.email=C.email)
		begin
			select @errno  = 50002,
                    @errmsg = 'email exist choose another.'
             goto error
		end
	if exists ( select * from inserted i,Manager M where i.EMAIL=M.EMAIL)
		begin
			select @errno  = 50002,
                    @errmsg = 'email exist choose another.'
             goto error
		end
	declare @name varchar(50),@pass varchar(500),@email varchar(100)
	set @name=(select Cname from inserted)
	set @pass=(select Cpass from inserted)
	set @email=(select Email from inserted)
	insert into Client values(@name,HASHBYTES('SHA1',@pass),@email)
	print 'Client insertion complete'
    return
/*  Errors handling  */
error:
	exec sp_addmessage @errno,16,@errmsg
    raiserror (@errno,16,1)
    rollback  transaction
	insert into trace values (SYSTEM_USER,USER,'Client','insert',GETDATE())
end
go

create trigger TI_Product on Product instead of insert as
begin
set nocount on
    declare
       @errno    int,
       @errmsg   varchar(255)

	if exists ( select * from Product P,inserted i where i.Pname=P.Pname)
		begin
			select @errno  = 50002,
                    @errmsg = 'Product name exist choose another.'
             goto error
		end
	declare @name varchar(50),@image varchar(200),@price float,@type varchar(50)
	set @name=(select Pname from inserted)
	set @image=(select Pimage from inserted)
	set @price=(select Pprice from inserted)
	set @type=(select Ptype from inserted)
	insert into Product values(@type,@name,@price,@image)
	print 'Product insertion complete'
    return
/*  Errors handling  */
error:
	exec sp_addmessage @errno,16,@errmsg
    raiserror (@errno,16,1)
    rollback  transaction
	insert into trace values (SYSTEM_USER,USER,'Product','insert',GETDATE())
end
go

create nonclustered index HAVE_FK on CREDITCARD (CID ASC)
go

create nonclustered index ORDERITEMS_FK on ORDERITEMS (PID ASC)
go

create nonclustered index ORDERITEMS2_FK on ORDERITEMS (OID ASC)
go

create nonclustered index HAS_FK on ORDERS (CID ASC)
go

create nonclustered index FOR_FK on ORDERS (DID ASC)
go

CREATE  view Sandwich as
(select Pname,Pprice,Pimage from Product where Ptype='Sandwich');
go
CREATE  view Drink as
(select Pname,Pprice,Pimage from Product where Ptype='Drink');
go

insert into manager values('admin','admin','AbuAli.Way@outlook.com',1)