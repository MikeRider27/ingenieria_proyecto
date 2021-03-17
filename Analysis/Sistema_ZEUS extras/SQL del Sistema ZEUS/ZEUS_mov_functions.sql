---------------------------------------------------------------------------------------------------------------------------------------------------MOD. COMPRAS---------
-----------------------------------------------------------------------------------PEDIDO DE MATERIALES-------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION abm_ped(
    ban integer,
    cod integer,
    emp	integer,
    suc integer)
  RETURNS integer AS
$BODY$
declare
--find int=0;
existe_cod int=0;
begin
CASE ban
      WHEN 1 THEN
          select count(1) into existe_cod from pedido where ped_cod=cod;
      	if existe_cod>0 then
          select coalesce(max(ped_cod),0)+1 into cod from pedido;
          raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
        end if;
	  INSERT INTO pedido VALUES (cod,1,emp,suc,current_timestamp);--SELECT * FROM PEDIDO
          raise notice '%','NOPE';
          return 0;
      
      WHEN 2 THEN
	UPDATE pedido SET est_cod=3 WHERE ped_cod=cod; 
	raise notice '%','NOPE';
	return 0;
	
      WHEN 3 THEN
	  select count(1) into existe_cod from v_ordc where ped_cod=cod and est_des='VIG';
        if existe_cod >0 then 
	  raise notice '%','No se puede Anular un Pedido de Materiales que ya posee Orden de Compra';
	  return 1;
        else 
	  UPDATE pedido SET est_cod=0 WHERE ped_cod=cod;
	  raise notice '%','NOPE';
	  return 0;
        end if;
      
      WHEN 4 THEN UPDATE pedido SET est_cod=5 WHERE ped_cod=cod;
        raise notice '%','NOPE';
        return 0;

      WHEN 5 THEN UPDATE pedido SET est_cod=4 WHERE ped_cod=cod; 
        raise notice '%','NOPE';
        return 0;

      WHEN 6 THEN UPDATE detalle_ped SET det_ord=FALSE WHERE ped_cod=cod;
        raise notice '%','NOPE';
        return 0;
end case;

    end;
    $BODY$
  LANGUAGE plpgsql;
  
-----------------------------------------------------------------------------------DETALLE DE PEDIDO DE MATERIALES-------------------------------------------------------------------------------------------------
--select * from detalle_ped
  CREATE OR REPLACE FUNCTION abm_dped(
    ban integer,
    cod integer,
    mat integer,
    cant numeric)
  RETURNS void AS
$BODY$
--declare
--find int=0;
begin
CASE ban
        WHEN 1 THEN 
--select count(1) into find from detalle_ped where mat_cod=mat and ped_cod=cod;
--if find >0 then
--		UPDATE detalle_ped SET det_cant=det_cant+cant WHERE ped_cod=cod AND mat_cod=mat;
--else
		INSERT INTO detalle_ped VALUES(cod,mat,2,cant,FALSE);
--end if;

      WHEN 4 THEN UPDATE detalle_ped SET est_cod=0 WHERE ped_cod=cod AND mat_cod=mat;--rechaza

      WHEN 5 THEN UPDATE detalle_ped SET est_cod=3 WHERE ped_cod=cod AND mat_cod=mat;--aprueba

      WHEN 6 THEN UPDATE detalle_ped SET det_ord=TRUE WHERE ped_cod=cod AND mat_cod=mat;--pone bandera de "con orden"
      
      WHEN 7 THEN UPDATE detalle_ped SET det_cant=cant,est_cod=3 WHERE ped_cod=cod AND mat_cod=mat;--aprueba y modifica cant

      WHEN 8 THEN UPDATE detalle_ped SET det_ord=FALSE WHERE ped_cod=cod AND mat_cod=mat;--pone bandera de "sin orden"
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
  --------------------------------------------------------------------------------------pedido de repuestos------------------------------------------------------------------
CREATE OR REPLACE FUNCTION abm_pedr(
    ban integer,
    cod integer,
    emp	integer,
    suc integer)
  RETURNS integer AS
$BODY$
declare
--find int=0;
existe_cod int=0;
begin
CASE ban
      WHEN 1 THEN
          select count(1) into existe_cod from pedido_r where ped_cod=cod;
      	if existe_cod>0 then
          select coalesce(max(ped_cod),0)+1 into cod from pedido_r;
	  --INSERT INTO pedido VALUES (cod,emp,suc,current_timestamp,'ING');
          raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
        end if;
	  INSERT INTO pedido_r VALUES (cod,1,emp,suc,current_timestamp);
          raise notice '%','NOPE';
          return 0;
      
      WHEN 2 THEN
	UPDATE pedido_r SET est_cod=3 WHERE ped_cod=cod; 
	raise notice '%','NOPE';
	return 0;
	
      WHEN 3 THEN
	  select count(1) into existe_cod from pedido_r p,ord_compra_r o where p.ped_cod=cod and o.ped_cod=p.ped_cod and o.ord_estado='EMI';
        if existe_cod >0 then 
	  raise notice '%','No se puede Anular un Pedido de Repuestos que ya posee Orden de Compra';
	  return 1;
        else 
	  UPDATE pedido_r SET est_cod=0 WHERE ped_cod=cod;
	  raise notice '%','NOPE';
	  return 0;
        end if;
      
      WHEN 4 THEN UPDATE pedido_r SET est_cod=5 WHERE ped_cod=cod;
        raise notice '%','NOPE';
        return 0;

      WHEN 5 THEN UPDATE pedido_r SET est_cod=4 WHERE ped_cod=cod; 
        raise notice '%','NOPE';
        return 0;

      WHEN 6 THEN UPDATE detalle_ped_r SET det_ord=FALSE WHERE ped_cod=cod;
        raise notice '%','NOPE';
        return 0;
end case;

    end;
    $BODY$
  LANGUAGE plpgsql;
--select * from v_dped
--select mat_cod,mat_des,det_cant from v_dped where ped_cod=1
 -----------------------------------------------------------------------------------detalle de pedido de repuestos---------------------------------------------------------------------------
  CREATE OR REPLACE FUNCTION abm_dpedr(--select * from detalle_ped_r
    ban integer,
    cod integer,
    rep integer,
    cant numeric)
  RETURNS void AS
$BODY$
--declare
--find int=0;
begin
CASE ban
      WHEN 1 THEN INSERT INTO detalle_ped_r VALUES(cod,rep,1,cant,FALSE);

      WHEN 4 THEN UPDATE detalle_ped_r SET est_cod=0 WHERE ped_cod=cod AND rep_cod=rep;--rechaza

      WHEN 5 THEN UPDATE detalle_ped_r SET est_cod=3 WHERE ped_cod=cod AND rep_cod=rep;--aprueba

      WHEN 6 THEN UPDATE detalle_ped_r SET det_ord=TRUE WHERE ped_cod=cod AND rep_cod=rep;--pone bandera de "con orden"
      
      WHEN 7 THEN UPDATE detalle_ped_r SET det_cant=cant,est_cod=3 WHERE ped_cod=cod AND rep_cod=rep;--aprueba y modifica cant

      WHEN 8 THEN UPDATE detalle_ped_r SET det_ord=FALSE WHERE ped_cod=cod AND rep_cod=rep;--pone bandera de "sin orden"
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
-------------------------------------------------------------------------------------ORDEN DE COMPRA---------------------------------------------------------------------------------------------------
--select * from ord_compra

CREATE OR REPLACE FUNCTION abm_ordc(--select * from ord_compra--select * from v_com--select abm_ordc(3,3,1,4,0,0,0)
    ban integer,
    cod integer,
    suc integer,
    ped integer,
    emp integer,
    prov integer,
    tip integer)
  RETURNS integer AS
$BODY$
declare
--find int=0;
existe_cod int=0;
begin
CASE ban
      WHEN 1 THEN
          select count(1) into existe_cod from ord_compra where ord_nro=cod;
      	if existe_cod>0 then
          select coalesce(max(ord_nro),0)+1 into cod from ord_compra;
          --INSERT INTO ord_compra VALUES (cod,ped,emp,prov,tip,current_timestamp,'EMI'); SELECT COUNT(1) FROM v_dordc WHERE ped_cod=1 and ord_estado!='ANU';
          raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
        end if;
          INSERT INTO ord_compra VALUES (cod,1,suc,ped,emp,prov,tip,current_timestamp);		
          raise notice '%','NOPE';
          return 0;
          
      WHEN 2 THEN UPDATE ord_compra SET est_cod=1 WHERE ord_nro=cod;
	  raise notice '%','NOPE';
          return 0;
	
      WHEN 3 THEN
	select count(1) into existe_cod from v_com where ord_nro=cod and est_des='VIG';
        if existe_cod >0 then 
	  raise notice '%','No se puede Anular una Orden de Compra de Materiales que ya posee Compra';
	  return 1;
        else --no posee ninguna compra aun
		UPDATE ord_compra SET est_cod=0 WHERE ord_nro=cod;--anula
        if ped>0 then
		SELECT COUNT(1) into existe_cod FROM v_ordc WHERE ped_cod=ped and est_des!='ANU';--select count(1) from v_ordc WHERE ped_cod=4 and est_des!='ANU';--select * from v_ped
	  
					if existe_cod>0 then
						UPDATE pedido SET est_cod=4 WHERE ped_cod=ped;--ord.p
					else
						UPDATE pedido SET est_cod=3 WHERE ped_cod=ped;--apr
					end if;
        end if;
	  raise notice '%','NOPE';
	  return 0;
      end if;
      WHEN 4 THEN UPDATE ord_compra SET est_cod=6 WHERE ord_nro=cod;
        raise notice '%','NOPE';
        return 0;

      WHEN 5 THEN UPDATE ord_compra SET est_cod=7 WHERE ord_nro=cod; 
        raise notice '%','NOPE';
        return 0;

      WHEN 6 THEN IF ped>0 then UPDATE detalle_ped SET det_ord=FALSE WHERE ped_cod=cod;
        raise notice '%','NOPE';
        return 0;
        end if;
end case;

    end;
    $BODY$
  LANGUAGE plpgsql ;

---------------------------------------------------------------------------detalle orden de compra------------------------------------------select * from detalle_ped
 CREATE OR REPLACE FUNCTION abm_dordc(--select * from detalle_ord_compra
    ban integer,
    cod integer,
    mat integer,
    ped integer,
    pre integer,
    cant numeric)
  RETURNS void AS
$BODY$
begin
CASE ban
      WHEN 1 THEN INSERT INTO detalle_ord_compra VALUES(cod,mat,pre,cant,false);
			IF ped>0 THEN 
			UPDATE detalle_ped SET det_ord=true WHERE ped_cod=ped and mat_cod=mat;
			END IF;

      WHEN 2 THEN UPDATE detalle_ord_compra SET det_com=true WHERE ord_nro=cod and mat_cod=mat;

      WHEN 3 THEN IF ped>0 THEN 
		  UPDATE detalle_ped SET det_ord=false WHERE ped_cod=ped and mat_cod=mat;
		  END IF;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;

-----------------------------------------------------------------------------------COMPRA-------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION abm_com(--select * from compra--select * from estado
    ban integer,
    cod integer,
    suc integer,
    emp	integer,
    ord integer,
    nro integer)
  RETURNS integer AS
$BODY$
declare
--find int=0;
existe_cod int=0;
begin
CASE ban
      WHEN 1 THEN
          select count(1) into existe_cod from compra where com_cod=cod;
      	if existe_cod>0 then
          select coalesce(max(com_cod),0)+1 into cod from compra;
          raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
        end if;
	  INSERT INTO compra VALUES (cod,1,suc,emp,ord,nro,current_timestamp);
	  
          raise notice '%','NOPE';
          return 0;
	
      WHEN 3 THEN
	  if ord>0 then--es segun una orden
			select count(1) into existe_cod from v_com where ord_nro=ord and est_des!='ANU';
			if existe_cod>0 then --com.p
				update ord_compra set est_cod=6 where ord_nro=ord;
			else --vig
				update ord_compra set est_cod=1 where ord_nro=ord;
			end if;
		end if;
	  UPDATE compra SET est_cod=0 WHERE com_cod=cod;
	  raise notice '%','NOPE';
	  return 0;
       -- end if;
end case;

    end;
    $BODY$
  LANGUAGE plpgsql;

--------------------------------------------------------------------------DETALLE DE COMPRA---------------------------------------------------------------  
 CREATE OR REPLACE FUNCTION abm_dcom(--select * from detalle_com -- select * from stock_mat
    ban integer,
    cod integer,
    mat integer,
    dep integer,
    suc integer,
    pre integer,
    cant_ numeric,
    ord integer)
  RETURNS void AS
$BODY$
declare
--find int=0;
existe_cod int=0;
begin
CASE ban
      WHEN 1 THEN ----------------------------------------------------------AGREGADO----------------------------------------------------
        select count(1) into existe_cod from stock_mat where mat_cod=mat and dep_cod=dep and suc_cod=suc;--cuenta dicho material en dicha suc y dep
      	  if existe_cod>0 then
            update stock_mat set cant=cant+cant_ where suc_cod=suc and dep_cod=dep and mat_cod=mat;--suma
		  else
            insert into stock_mat values (suc,dep,mat,cant_,0);--inserta--select * from stock_mat
          end if;	
      INSERT INTO detalle_com VALUES(cod,mat,dep,suc,pre,cant_);
        if ord>0 then--si tiene orden actualiza las banderas de los detalles --select * from detalle_ord_compra
		update detalle_ord_compra set det_com=true where ord_nro=ord and mat_cod=mat;
        end if;		
	raise notice '%','NOPE';
	
      WHEN 3 THEN -----------------------------------------------------------ANULADO---------------------------------------------------
      IF ord>0 THEN
       UPDATE detalle_ord_compra SET det_com=false WHERE ord_nro=ord and mat_cod=mat;
      END IF;
		select cant into existe_cod from stock_mat where mat_cod=mat and dep_cod=dep and suc_cod=suc;--consulta la cantidad en stock actual---------
      	if existe_cod>=cant_ then--es mayor o igual--------------(BIEN)-------------------------------------------------------------------------------------
					update stock_mat set cant=cant-cant_ where suc_cod=suc and dep_cod=dep and mat_cod=mat;
					raise notice '%','NOPE';
		   else ------------no es mayor o igual----------(MAL)--------------------------------------------------------------------------------------
	if existe_cod=0 then----no hay nada en existencia del mat a anular	

		   else--------es menor la existencia a cant_----(MAL)--------------------------------------------------------------------------------------

	       end if;
         end if;	
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
---------------------------------------------------------------------------detalle de orden de compra------------------------------------------select * from detalle_ped
 CREATE OR REPLACE FUNCTION abm_dordc(--select * from detalle_ord_compra
    ban integer,
    cod integer,
    mat integer,
    ped integer,
    pre integer,
    cant numeric)
  RETURNS void AS
$BODY$
begin
CASE ban
      WHEN 1 THEN INSERT INTO detalle_ord_compra VALUES(cod,mat,ped,pre,cant,true);
			UPDATE detalle_ped SET det_ord=true WHERE ped_cod=ped and mat_cod=mat;

      WHEN 2 THEN UPDATE detalle_ord_compra SET det_com=true WHERE ord_nro=cod and mat_cod=mat;

      WHEN 3 THEN UPDATE detalle_ped SET det_ord=false WHERE ped_cod=ped and mat_cod=mat;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;


--------------------------------------------------------------------------------------------compra repuestos-+-----------------------------------------------------
CREATE OR REPLACE FUNCTION abm_comr(--select * from compra_r **********************FALTA
    ban integer,
    cod integer,
    emp	integer,
    ped integer,
    ord integer,
    nro integer)
  RETURNS integer AS
$BODY$
declare
--find int=0;
existe_cod int=0;
begin
CASE ban
      WHEN 1 THEN
          select count(1) into existe_cod from compra_r where com_cod=cod;
      	if existe_cod>0 then
          select coalesce(max(com_cod),0)+1 into cod from compra_r;
          raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
        end if;
	  INSERT INTO compra_r VALUES (cod,1,emp,ped,ord,nro,current_timestamp);
          raise notice '%','NOPE';
          return 0;
	
      WHEN 3 THEN
	  --select count(1) into existe_cod from pedido p,ord_compra o where p.ped_cod=cod and o.ped_cod=p.ped_cod and o.ord_estado='EMI';
        --if existe_cod >0 then 
	--  raise notice '%','No se puede Anular un Pedido de Materiales que ya posee Orden de Compra';
	--  return 1;
       -- else 
	  UPDATE compra_r SET com_null=true WHERE com_cod=cod;
	  raise notice '%','NOPE';
	  return 0;
       -- end if;
end case;

    end;
    $BODY$
  LANGUAGE plpgsql;

  -----------------------------------------------------------------------------------AJUSTE DE MATERIALES-------------------------------------------------------------------------------------------------
--select * from ajuste_m
CREATE OR REPLACE FUNCTION abm_ajum(ban integer,cod integer,emp integer)RETURNS int AS--select * from ajuste_m
$BODY$
declare
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN
        select count(1) into existe_cod from ajuste_m where aju_cod=cod;
		if existe_cod>0 then
select coalesce(max(aju_cod),0)+1 into cod from ajuste_m;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
		end if;
      INSERT INTO ajuste_m VALUES (cod,1,emp,current_date);
      raise notice '%','NOPE';
      return 0;
      
      WHEN 3 THEN UPDATE ajuste_m SET est_cod=0 WHERE aju_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;

-----------------------------------------------------------------------------------DETALLE DE AJUSTE MATERIALES-------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION abm_dajum(--select * from detalle_ajuste_m--delete from 
    ban integer,
    cod integer,
    mat integer,
    dep integer,
    suc integer,
    mot integer,
    cant_ numeric,
    tmot integer)
  RETURNS void AS
$BODY$
declare
find int=0;--para determinar el update o insert en base a lo previamente agregado en dicho ajuste y las validaciones correspondientes
find2 int=0;--para comprobar que no ingrese valores negativos en un stock que es 0 o que al restarle la cant de resultado menos de 0
begin
CASE ban
        WHEN 1 THEN 
    if tmot>1 then-------------------------------------es un ajuste positivo------------------------------------------------------------------------
select count(1) into find from v_dajum where mat_cod=mat and aju_cod=cod and dep_cod=dep and suc_cod=suc and mot_cod=mot and tmot_cod!=tmot;--verifica si se ingreso un detalle negativo previamente (anularia el corriente detalle)
--select count(1) into find2 from v_dajum where mat_cod=mat and aju_cod=cod and dep_cod=dep and suc_cod=suc and mot_cod=mot and tmot_cod=tmot;--verifica si se ingreso un detalle positivo previamente (para hacer un update y no insert)
	if find>0 then
		--raise notice '%','No se puede guardar detalles con valores opuestos';
		--return 1;
	else
insert into detalle_ajuste_m values(cod,mat,dep,suc,mot,tmot,cant_);
update stock_mat set cant=cant+cant_ where suc_cod=suc and dep_cod=dep and mat_cod=mat;
	--raise notice '%','NOPE';
      --  return 0;
end if;

	else----------------------------------------------es un ajuste negativo---------------------------------------------------------
select cant-cant_ into find2 from stock_mat where mat_cod=mat and dep_cod=dep and suc_cod=suc;--magiaa, cuenta si al restar queda menos de cero
select count(1) into find from detalle_ajuste_m where mat_cod=mat and aju_cod=cod and dep_cod=dep and suc_cod=suc and mot_cod=mot and tmot_cod!=tmot;
if find>0 then --hay valores opuestos
		--raise notice '%','No se puede guardar detalles con valores opuestos';
		---return 3;
else -- no hay valores opuestos
if find2<=0 then -- la resta del stock de la cant a ajustar da 0 o menos de 0
		--raise notice '%','No se puede guardar detalles que den como resultado valores nulos o negativos en la Existencia';
		--return 4;
else -- es un ajuste correcto
insert into detalle_ajuste_m values(cod,mat,dep,suc,mot,tmot,cant_);--select * from detalle_ajuste_m
update stock_mat set cant=cant-cant_ where suc_cod=suc and dep_cod=dep and mat_cod=mat;
	--raise notice '%','NOPE';
       -- return 0;
--end if;
end if;
end if;
end if;--SELECT * from stock_mat

      WHEN 3 THEN 

      if tmot>1 then--positivo
			update stock_mat set cant=cant-cant_ where suc_cod=suc and dep_cod=dep and mat_cod=mat;
      else--negativo
			update stock_mat set cant=cant+cant_ where suc_cod=suc and dep_cod=dep and mat_cod=mat;
      end if;
     -- raise notice '%','NOPE';
     -- return 0;

end case;
    end;
    $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

  ---------------------------------------------------------------------------------------------------------------------REMISION DE MATERIALES
  CREATE OR REPLACE FUNCTION abm_rem (ban integer,cod integer,suc integer,emp integer)RETURNS int AS--select * from remision
$BODY$
declare
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN
        select count(1) into existe_cod from remision where rem_cod=cod;
	if existe_cod>0 then
select coalesce(max(rem_cod),0)+1 into cod from remision;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO remision VALUES (cod,suc,1,emp,current_date);
      raise notice '%','NOPE';
      return 0;

      WHEN 2 THEN UPDATE remision SET est_cod=8 WHERE rem_cod=cod;
      raise notice '%','NOPE';
      return 0;

      WHEN 3 THEN UPDATE remision SET est_cod=0 WHERE rem_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
  -------------------------------------------------------------------------------------------------------------------------DETALLE REMISION DE MATERIALES
  CREATE OR REPLACE FUNCTION abm_drem(--
    ban integer,
    cod integer,
    mat integer,
    dep integer,
    suc integer,
    suc_o integer,
    dep_o integer,
    cant_ numeric,
    estado character varying)
  RETURNS void AS
$BODY$
declare var int=0;
begin
CASE ban
      WHEN 1 THEN --al insertar elimina del stock local
select cant into var from v_stock_m where suc_cod=suc_o and mat_cod=mat and dep_cod=dep_o;--consulta cuanto hay en existencia
		if var>=cant_ THEN--solo si es mayor o igual la existencia procede a la remision
				UPDATE stock_mat SET cant=cant-cant_ WHERE mat_cod=mat and dep_cod=dep_o and suc_cod=suc_o;--resta del stock local
select count(1) into var from stock_mat where suc_cod=suc and mat_cod=mat and dep_cod=dep;--pregunta si existe el mat en la suc destino
			if var>0 THEN
				UPDATE stock_mat SET cant_no_disp=cant_no_disp+cant_ WHERE mat_cod=mat and dep_cod=dep and suc_cod=suc;--actualiza el registro de cant no disponible, ya que aun no llego los mat alli
			else
				INSERT INTO stock_mat VALUES(suc,dep,mat,0,cant_);--inserta en el caso de que no haya dicho mat en la suc destino, como cant no disponible
			end if;
				INSERT INTO detalle_rem VALUES(cod,mat,dep,suc,suc_o,dep_o,cant_);--inserta el detalle en la remision
		end if;	
      WHEN 2 THEN--al actualizar, confirma que llegaron TODOS los materiales a destino, suma a el stock destino y resta de la cant no disponible
--select count(1) into var from stock_mat where suc_cod=suc and mat_cod=mat and dep_cod=dep;
				UPDATE stock_mat SET cant=cant+cant_,cant_no_disp=cant_no_disp-cant_ WHERE mat_cod=mat and dep_cod=dep and suc_cod=suc;
      WHEN 3 THEN --agrega de nuevo al stock local 
		if(estado='PROV') then--ya se marco como recibido
				UPDATE stock_mat SET cant=cant+cant_ WHERE mat_cod=mat and dep_cod=dep_o and suc_cod=suc_o;--agrega local
				UPDATE stock_mat SET cant=cant-cant_ WHERE mat_cod=mat and dep_cod=dep and suc_cod=suc;--resta destino
		else--no se marco como recibido
				UPDATE stock_mat SET cant=cant+cant_ WHERE mat_cod=mat and dep_cod=dep_o and suc_cod=suc_o;--agrega local
				UPDATE stock_mat SET cant_no_disp=cant_no_disp-cant_ WHERE mat_cod=mat and dep_cod=dep and suc_cod=suc;--resta del no disponible del destino
		end if;				
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
  
  -----------------------------------------------------------------------------------AJUSTE DE REPUESTOS-------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION abm_ajur(ban integer,cod integer,emp integer)RETURNS int AS
$BODY$
declare
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into existe_cod from ajuste_r where aju_cod=cod;
	if existe_cod>0 then
select coalesce(max(aju_cod),0)+1 into cod from ajuste_r;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO ajuste_r VALUES (cod,1,emp,current_date);
      raise notice '%','NOPE';
      return 0;
--end if;
      WHEN 3 THEN UPDATE ajuste_r SET est_cod=0 WHERE aju_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;