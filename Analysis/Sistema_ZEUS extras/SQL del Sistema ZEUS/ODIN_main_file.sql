------ref inserts
--estado--select * from usuario
insert into estado values (0,'VIG',false),(1,'ANU',false);
--usuario--
insert into usuario values (0,0,'ROOT','202cb962ac59075b964b07152d234b70');
--egreso
insert into egreso values (1,0,'PASAJE'),(2,0,'ALMUERZO'),(3,0,'MERIENDA TIGO'),(4,0,'ABU'),(5,0,'MALCRIACION ._.');
--ingreso
insert into ingreso values (1,0,'SALARIO'),(2,0,'ABU'),(3,0,'NANCY BAEZ');

------views
--estado--
create or replace view v_est as(select * from estado);
--usuario--
create or replace view v_log as(select * from usuario);
--egreso
create or replace view v_egr as(select e.egr_cod,e.egr_des,es.est_des from egreso e,estado es where e.est_cod=es.est_cod);
--ingreso
create or replace view v_ing as(select e.ing_cod,e.ing_des,es.est_des from ingreso e,estado es where e.est_cod=es.est_cod);
--mov_egreso
--detalle_egreso
--mov_ingreso
--detalle_ingreso


------procedures
--egreso
CREATE OR REPLACE FUNCTION abm_egr(ban integer,cod integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN -----------------------------------------------------------------
select count(1) into find from egreso where egr_des=des;
if find >0 then
		raise notice '%','Ya existe un Egreso con esa Descripción';
		return 1;
else
select count(1) into find from egreso where egr_cod=cod;
	if find>0 then
select coalesce(max(egr_cod),0)+1 into cod from egreso;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO egreso VALUES (cod,0,upper(des));
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN -----------------------------------------------------------------
select count(1) into find from egreso where egr_des=des and egr_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe un Egreso con esa Descripción';
		return 1;
else
             UPDATE egreso SET egr_des=upper(des) WHERE egr_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN -----------------------------------------------------------------
update egreso set est_cod=1 WHERE egr_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
  
--ingreso
CREATE OR REPLACE FUNCTION abm_ing(ban integer,cod integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN -----------------------------------------------------------------
select count(1) into find from ingreso where ing_des=des;
if find >0 then
		raise notice '%','Ya existe un Ingreso con esa Descripción';
		return 1;
else
select count(1) into find from ingreso where ing_cod=cod;
	if find>0 then
select coalesce(max(ing_cod),0)+1 into cod from ingreso;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO ingreso VALUES (cod,0,upper(des));
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN -----------------------------------------------------------------
select count(1) into find from ingreso where ing_des=des and ing_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe un Ingreso con esa Descripción';
		return 1;
else
             UPDATE ingreso SET ing_des=upper(des) WHERE ing_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN -----------------------------------------------------------------
update ingreso set est_cod=1 WHERE ing_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;

--usuario
CREATE OR REPLACE FUNCTION abm_usr(ban integer,cod integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN -----------------------------------------------------------------
select count(1) into find from ingreso where ing_des=des;
if find >0 then
		raise notice '%','Ya existe un Ingreso con esa Descripción';
		return 1;
else
select count(1) into find from ingreso where ing_cod=cod;
	if find>0 then
select coalesce(max(ing_cod),0)+1 into cod from ingreso;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO ingreso VALUES (cod,0,upper(des));
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN -----------------------------------------------------------------
select count(1) into find from ingreso where ing_des=des and ing_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe un Ingreso con esa Descripción';
		return 1;
else
             UPDATE ingreso SET ing_des=upper(des) WHERE ing_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN -----------------------------------------------------------------
update ingreso set est_cod=1 WHERE ing_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;

--mov_egreso
CREATE OR REPLACE FUNCTION abm_m_egr(ban integer,cod integer,fecha date)RETURNS int AS
$BODY$
declare
--find int=0;
begin
CASE ban
        WHEN 1 THEN -----------------------------------------------------------------
--select count(1) into find from mov_egreso where ing_des=des;
--if find >0 then
	--	raise notice '%','Ya existe un Ingreso con esa Descripción';
	--	return 1;
--else
--select count(1) into find from ingreso where ing_cod=cod;
--	if find>0 then
--select coalesce(max(ing_cod),0)+1 into cod from ingreso;
--raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
--end if;
      INSERT INTO mov_egreso VALUES (cod,0,fecha);
      raise notice '%','NOPE';
      return 0;
--end if;
      WHEN 3 THEN -----------------------------------------------------------------
update mov_egreso set est_cod=1 WHERE mov_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;

  --detalle_egreso
 CREATE OR REPLACE FUNCTION abm_degr(--select * from detalle_egreso
    ban integer,
    cod integer,
    egr integer,
    cant_ numeric)
  RETURNS void AS
$BODY$
declare
find int=0;
begin
CASE ban

      WHEN 1 THEN
select count(1) into find from detalle_egreso where mov_cod=cod and egr_cod=egr;
if find >0 then
 update detalle_egreso set cant=cant+cant_ where mov_cod=cod and egr_cod=egr;
else
 INSERT INTO detalle_egreso VALUES(cod,mat,pre,cant,false);
end if;
    --  WHEN 3 THEN UPDATE detalle_ped SET det_ord=false WHERE ped_cod=ped and mat_cod=mat;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;