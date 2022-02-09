/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2017                    */
/* Created on:     01/09/2021 1:33:21 PM                        */
/*==============================================================*/
use foodDB
go


create trigger TI_CREDITCARD on CREDITCARD for insert as
begin
    declare
       @numrows  int,
       @numnull  int,
       @errno    int,
       @errmsg   varchar(255)

    select  @numrows = @@rowcount
    if @numrows = 0
       return

    /*  Parent "CLIENT" must exist when inserting a child in "CREDITCARD"  */
    if update(CID)
    begin
       if (select count(*)
           from   CLIENT t1, inserted t2
           where  t1.CID = t2.CID) != @numrows
          begin
             select @errno  = 50002,
                    @errmsg = 'Parent does not exist in "CLIENT". Cannot create child in "CREDITCARD".'
             goto error
          end
    end

    return

/*  Errors handling  */
error:
    raiserror (@errno,@errmsg,16)
    rollback  transaction
end
go


create trigger TU_CREDITCARD on CREDITCARD for update as
begin
   declare
      @numrows  int,
      @numnull  int,
      @errno    int,
      @errmsg   varchar(255)

      select  @numrows = @@rowcount
      if @numrows = 0
         return

      /*  Parent "CLIENT" must exist when updating a child in "CREDITCARD"  */
      if update(CID)
      begin
         if (select count(*)
             from   CLIENT t1, inserted t2
             where  t1.CID = t2.CID) != @numrows
            begin
               select @errno  = 50003,
                      @errmsg = 'CLIENT" does not exist. Cannot modify child in "CREDITCARD".'
               goto error
            end
      end

      return

/*  Errors handling  */
error:
    raiserror (@errno,@errmsg,16)
    rollback  transaction
end
go


create trigger TI_ORDER on "ORDER" for insert as
begin
    declare
       @numrows  int,
       @numnull  int,
       @errno    int,
       @errmsg   varchar(255)

    select  @numrows = @@rowcount
    if @numrows = 0
       return

    /*  Parent "CLIENT" must exist when inserting a child in ""ORDER""  */
    if update(CID)
    begin
       if (select count(*)
           from   CLIENT t1, inserted t2
           where  t1.CID = t2.CID) != @numrows
          begin
             select @errno  = 50002,
                    @errmsg = 'Parent does not exist in "CLIENT". Cannot create child in ""ORDER"".'
             goto error
          end
    end

    return

/*  Errors handling  */
error:
    raiserror (@errno,@errmsg,16)
    rollback  transaction
end
go


create trigger TU_ORDER on "ORDER" for update as
begin
   declare
      @numrows  int,
      @numnull  int,
      @errno    int,
      @errmsg   varchar(255)

      select  @numrows = @@rowcount
      if @numrows = 0
         return

      /*  Parent "CLIENT" must exist when updating a child in ""ORDER""  */
      if update(CID)
      begin
         if (select count(*)
             from   CLIENT t1, inserted t2
             where  t1.CID = t2.CID) != @numrows
            begin
               select @errno  = 50003,
                      @errmsg = 'CLIENT" does not exist. Cannot modify child in ""ORDER"".'
               goto error
            end
      end

      return

/*  Errors handling  */
error:
    raiserror (@errno,@errmsg,16)
    rollback  transaction
end
go


create trigger TI_ORDERITEMS on ORDERITEMS for insert as
begin
    declare
       @numrows  int,
       @numnull  int,
       @errno    int,
       @errmsg   varchar(255)

    select  @numrows = @@rowcount
    if @numrows = 0
       return

    /*  Parent "PRODUCT" must exist when inserting a child in "ORDERITEMS"  */
    if update(PID)
    begin
       if (select count(*)
           from   PRODUCT t1, inserted t2
           where  t1.PID = t2.PID) != @numrows
          begin
             select @errno  = 50002,
                    @errmsg = 'Parent does not exist in "PRODUCT". Cannot create child in "ORDERITEMS".'
             goto error
          end
    end
    /*  Parent ""ORDER"" must exist when inserting a child in "ORDERITEMS"  */
    if update(OID)
    begin
       if (select count(*)
           from   "ORDER" t1, inserted t2
           where  t1.OID = t2.OID) != @numrows
          begin
             select @errno  = 50002,
                    @errmsg = 'Parent does not exist in ""ORDER"". Cannot create child in "ORDERITEMS".'
             goto error
          end
    end

    return

/*  Errors handling  */
error:
    raiserror (@errno,@errmsg,16)
    rollback  transaction
end
go


create trigger TU_ORDERITEMS on ORDERITEMS for update as
begin
   declare
      @numrows  int,
      @numnull  int,
      @errno    int,
      @errmsg   varchar(255)

      select  @numrows = @@rowcount
      if @numrows = 0
         return

      /*  Parent "PRODUCT" must exist when updating a child in "ORDERITEMS"  */
      if update(PID)
      begin
         if (select count(*)
             from   PRODUCT t1, inserted t2
             where  t1.PID = t2.PID) != @numrows
            begin
               select @errno  = 50003,
                      @errmsg = 'PRODUCT" does not exist. Cannot modify child in "ORDERITEMS".'
               goto error
            end
      end
      /*  Parent ""ORDER"" must exist when updating a child in "ORDERITEMS"  */
      if update(OID)
      begin
         if (select count(*)
             from   "ORDER" t1, inserted t2
             where  t1.OID = t2.OID) != @numrows
            begin
               select @errno  = 50003,
                      @errmsg = '"ORDER"" does not exist. Cannot modify child in "ORDERITEMS".'
               goto error
            end
      end

      return

/*  Errors handling  */
error:
    raiserror (@errno,@errmsg,16)
    rollback  transaction
end
go

