-------------------------------------------------------------------------------------------------ban
create or replace view v_ban as(SELECT b.ban_cod,b.ban_des,e.est_des
from banco b,estado e
where e.est_cod=b.est_cod);
-------------------------------------------------------------------------------------------------mot
create or replace view v_mot as(select m.mot_cod,t.tmot_des,m.mot_des,e.est_des,m.tmot_cod
from motivo_ajuste m,tipo_mot_aju t,estado e 
where t.tmot_cod=m.tmot_cod and e.est_cod=m.est_cod);
-------------------------------------------------------------------------------------------------motg
create or replace view v_motg as(SELECT m.mot_cod,m.mot_des,e.est_des 
from motivo_garantia m, estado e 
WHERE m.est_cod=e.est_cod);
-------------------------------------------------------------------------------------------------car
create or replace view v_car as(SELECT c.car_cod,c.car_des,c.car_user,c.car_pass,e.est_des
from cargo c,estado e
where car_cod!=0 and c.est_cod=e.est_cod);
-------------------------------------------------------------------------------------------------ciu
create or replace view v_ciu as(SELECT c.ciu_cod,c.ciu_des,e.est_des
from ciudad c,estado e
where e.est_cod=c.est_cod);
-------------------------------------------------------------------------------------------------col
create or replace view v_col as(SELECT c.col_cod,c.col_des,e.est_des
from color c,estado e
where e.est_cod=c.est_cod);
-------------------------------------------------------------------------------------------------est
create or replace view v_est as(SELECT * from estado);
-------------------------------------------------------------------------------------------------tmot
create or replace view v_tmot as(SELECT t.tmot_cod,tmot_des,e.est_des
from tipo_mot_aju t,estado e
where e.est_cod=t.est_cod);
-------------------------------------------------------------------------------------------------comb
create or replace view v_comb as(SELECT c.comb_cod,c.comb_des,e.est_des
from combustible c,estado e
where e.est_cod=c.est_cod);
-------------------------------------------------------------------------------------------------dam
create or replace view v_dam as(SELECT d.dam_cod,d.dam_des,d.dam_verb,d.dam_mul,e.est_des 
from damage d,estado e
where e.est_cod=d.est_cod);
-------------------------------------------------------------------------------------------------eta
create or replace view v_eta as(SELECT e.eta_cod,e.eta_des,es.est_des
from etapa e,estado es
where es.est_cod=e.est_cod);
-------------------------------------------------------------------------------------------------mar
id_mar integer NOT NULL,
  descrip
create or replace view v_mar as(SELECT m.id_mar,m.descrip,e.est_des
from marca m,estados e
where e.est_cod=m.est_cod);
-------------------------------------------------------------------------------------------------mat
create or replace view v_mat as(SELECT m.mat_cod,m.un_cod,m.mat_des,m.mat_min,e.est_des
from material m,estado e
where e.est_cod=m.est_cod);
-------------------------------------------------------------------------------------------------param
create or replace view v_dmat as(SELECT dd.dam_des,m.mat_des,d.cant,e.est_des,d.mat_cod,concat(dd.dam_cod,' - ',dd.dam_des)as dam
from material m,estado e,damage dd,detalle_mat d
where e.est_cod=d.est_cod and m.mat_cod=d.mat_cod and dd.dam_cod=d.dam_cod);
-------------------------------------------------------------------------------------------------nac
create or replace view v_nac as(SELECT n.id_nac,n.descrip,e.descripc
from nacionalidad n,estado e
where e.id_est=n.id_est);
select * from estado
-------------------------------------------------------------------------------------------------prov
create or replace view v_prov as(SELECT p.prov_cod,p.prov_des,p.prov_dir,p.prov_tel,p.prov_mail,p.prov_ruc,e.est_des
from proveedor p,estado e
where e.est_cod=p.est_cod);
-------------------------------------------------------------------------------------------------provs
create or replace view v_provs as(SELECT p.prov_cod,p.prov_des,p.prov_dir,p.prov_tel,p.prov_mail,p.prov_ruc,e.est_des
from proveedor_serv p,estado e
where e.est_cod=p.est_cod);
-------------------------------------------------------------------------------------------------det_prov
--drop view v_dprovs
create or replace view v_dprovs as (SELECT
  t.trab_des,
  concat(t.trab_cod,' - ',t.trab_des)as trab,
  p.prov_cod,
  e.est_des
FROM trabajos_terciarios t,proveedor_serv p,detalle_prov d,estado e
WHERE p.prov_cod = d.prov_cod AND t.trab_cod = d.trab_cod and e.est_cod=d.est_cod);
-------------------------------------------------------------------------------------------------pza
create or replace view v_pzas as(SELECT p.pie_cod,p.pie_des,p.pie_cant,e.est_des
from pieza p,estado e
where e.est_cod=p.est_cod);
-------------------------------------------------------------------------------------------------seg
create or replace view v_seg as(SELECT s.seg_cod,s.seg_des,s.seg_dir,s.seg_tel,s.seg_mail,e.est_des
from seguro s,estado e
where e.est_cod=s.est_cod);
-------------------------------------------------------------------------------------------------suc
create or replace view v_suc as(SELECT s.suc_cod,s.suc_des,s.suc_dir,s.suc_tel,s.suc_mail,e.est_des
from sucursal s,estado e
where e.est_cod=s.est_cod);
-------------------------------------------------------------------------------------------------tpag
create or replace view v_tpag as(SELECT t.tip_cod,t.tip_des,e.est_des
from tipo_de_pago t,estado e
where e.est_cod=t.est_cod);
-------------------------------------------------------------------------------------------------trab
create or replace view v_trab as(SELECT t.trab_cod,t.trab_des,e.est_des
from trabajos_terciarios t,estado e
where e.est_cod=t.est_cod);
-------------------------------------------------------------------------------------------------trep
create or replace view v_trep as(SELECT t.trep_cod,t.trep_des,e.est_des
from tipo_rep t,estado e
where e.est_cod=t.est_cod);
-------------------------------------------------------------------------------------------------tveh
create or replace view v_tveh as(SELECT t.tveh_cod,t.tveh_des,e.est_des,concat(t.tveh_cod,' - ',t.tveh_des)as tveh
from tipo_veh t,estado e
where e.est_cod=t.est_cod);
-------------------------------------------------------------------------------------------------un
create or replace view v_un as(SELECT u.un_cod,u.un_des,e.est_des
from un_de_medida u,estado e
where e.est_cod=u.est_cod);
-------------------------------------------------------------------------------------------------caj
create or replace view v_caj as(SELECT c.caj_cod,c.caj_des,e.est_des
from caja c,estado e
where e.est_cod=c.est_cod);
-------------------------------------------------------------------------------------------------veh
create or replace view v_veh as(select 
v.veh_cod,
m.mar_des,
mo.mod_des,
c.col_des,
t.tveh_des,
v.veh_año,
v.veh_cha,
v.veh_chas,
e.est_des,
concat(m.mar_cod,' - ',m.mar_des)as mar,
concat(mo.mod_cod,' - ',mo.mod_des)as mod_,
concat(c.col_cod,' - ',c.col_des)as col,
concat(t.tveh_cod,' - ',t.tveh_des)as tveh,
concat(v.veh_cod,' - ',m.mar_des,', ',mo.mod_des,' ',c.col_des,' ',v.veh_año)as veh
from vehiculo v,marca m,modelo mo,tipo_veh t,color c,estado e
where m.mar_cod=v.mar_cod and mo.mod_cod=v.mod_cod and t.tveh_cod=v.tveh_cod and c.col_cod=v.col_cod and e.est_cod=v.est_cod);
-------------------------------------------------------------------------------------------------det_veh
create or replace view v_dveh as (select d.cli_ci,d.seg_cod,d.veh_cod,d.det_ter,d.det_pol,--add estado
concat(c.cli_ci,' - ',c.cli_nom,', ',c.cli_ape)as cli,
concat(s.seg_cod,' - ',s.seg_des)as seg,
v.veh_cha,v.veh_chas,v.veh
from detalle_veh d,cliente c,v_veh v,seguro s
where c.cli_ci=d.cli_ci and v.veh_cod=d.veh_cod and s.seg_cod=d.seg_cod); 
-------------------------------------------------------------------------------------------------bar
create or replace view v_bar as(SELECT
  b.bar_cod AS bar_cod,
  CONCAT(b.ciu_cod,' - ',c.ciu_des) AS ciu,
  b.bar_des AS bar_des,
  c.ciu_des AS ciu_des,
  e.est_des
FROM barrio b,ciudad c,estado e
WHERE c.ciu_cod=b.ciu_cod and e.est_cod=b.est_cod);
-------------------------------------------------------------------------------------------------cli
create or replace view v_cli as(select 
c.cli_ci,
n.nac_des,
ci.ciu_des,
b.bar_des,
c.cli_nom,
c.cli_ape,
c.cli_cel,
c.cli_dir,
c.cli_fnac,
e.est_des,
to_char(c.cli_fnac,'DD/MM/YYYY'),
concat(ci.ciu_cod,' - ',ci.ciu_des)as ciu,
concat(b.bar_cod,' - ',b.bar_des)as bar,
concat(n.nac_cod,' - ',n.nac_des)as nac,
concat(c.cli_ci,' - ',c.cli_nom,', ',c.cli_ape)as cli
from cliente c,nacionalidad n,ciudad ci,barrio b,estado e
where n.nac_cod=c.nac_cod and c.ciu_cod=ci.ciu_cod and b.bar_cod=c.bar_cod and e.est_cod=c.est_cod);
--SELECT date_part('year',age(birth)),* FROM persons LIMIT 1;
-------------------------------------------------------------------------------------------------rep
create or replace view v_rep as (SELECT
  r.rep_cod,
  CONCAT(t.trep_cod,' - ',t.trep_des) AS trep,
  r.rep_des,
  t.trep_des,
  e.est_des
FROM repuesto r,tipo_rep t,estado e
WHERE t.trep_cod = r.trep_cod and e.est_cod=r.est_cod);
-------------------------------------------------------------------------------------------------mod
create or replace view v_mod as (SELECT
  m.mod_cod,
  ma.mar_des,
  m.mod_des,
  e.est_des,
  CONCAT(ma.mar_cod,' - ',ma.mar_des) AS mar,
  CONCAT(m.mod_cod,' - ',m.mod_des) AS mod_
FROM modelo m,marca ma,estado e
WHERE ma.mar_cod = m.mar_cod and e.est_cod=m.est_cod);
-------------------------------------------------------------------------------------------------det_mod
--drop view v_dmod
create or replace view v_dmod as(SELECT
  m.mar_des,
  mo.mod_des,
  t.tveh_des,
  t.tveh_cod,
  concat(t.tveh_cod,' - ',t.tveh_des)as tveh,
  e.est_des,
  mo.mod_cod,
  m.mar_cod
FROM marca m,modelo mo,tipo_veh t,detalle_modelo d,estado e
WHERE m.mar_cod = d.mar_cod AND mo.mod_cod = d.mod_cod AND t.tveh_cod = d.tveh_cod and e.est_cod=d.est_cod);
-------------------------------------------------------------------------------------------------tim
--drop view v_tim
create or replace view v_tim as (select t.tim_cod,t.tim_nro,t.tim_venc,
to_char(t.tim_venc,'DD/MM/YYYY'),
e.est_des
from timbrado t,estado e
where e.est_cod=t.est_cod);
-------------------------------------------------------------------------------------------------dep
create or replace view v_dep as(SELECT
  d.dep_cod ,
  CONCAT(s.suc_cod,' - ',s.suc_des) AS suc,
  d.dep_des ,
  e.est_des,
  s.suc_des 
FROM deposito d,sucursal s,estado e
WHERE s.suc_cod = d.suc_cod and e.est_cod=d.est_cod);
-------------------------------------------------------------------------------------------------detalle_emp
create or replace view v_demp as(select 
s.suc_des,
d.date_ing,
d.date_egr,
d.emp_ci,
d.suc_cod
from sucursal s,empleado e,detalle_emp d
where s.suc_cod=d.suc_cod and e.emp_ci=d.emp_ci );
-------------------------------------------------------------------------------------------------emp 
--drop view v_func   
create or replace view v_func as (SELECT --select * from empleado
  e.emp_ci ,
  n.nac_des,
  CONCAT(c.car_cod,' - ',c.car_des) AS car,
  e.emp_nom,
  e.emp_ape,
  e.emp_cel,
  e.emp_dir,
  es.est_des,
  e.emp_user,
  e.emp_pass,
  CONCAT(n.nac_cod,' - ',n.nac_des) AS nac,
  CONCAT(d.suc_cod,' - ',d.suc_des) AS suc,
  e.emp_block,
  e.emp_fnac,
  d.date_ing,
  d.date_egr,
  to_char(e.emp_fnac,'DD/MM/YYYY')
FROM empleado e,nacionalidad n,cargo c,estado es,v_demp d
WHERE n.nac_cod = e.nac_cod AND c.car_cod = e.car_cod and es.est_cod=e.est_cod and d.emp_ci=e.emp_ci and d.date_egr isnull);
--****************************************************************************************************************************************************************************--
--****************************************************************************************************************************************************************************--
--****************************************************************************************************************************************************************************--
--****************************************************************************************************************************************************************************--
--****************************************************************************************************************************************************************************--
--****************************************************************************************************************************************************************************--

-------------------------------------------------------------------------------------------------prep
--select * from presupuesto
-------------------------------------------------------------------------------------------------rem --->ver luego, para que la suc agregue a su dep y no desde rem antes de enviar la carga
--create or replace view v_rem as(select r.rem_cod,s.suc_cod,,emp.emp_user,r.rem_date,e.est_des,r.emp_ci -->ver para que la carga no este en suc_o ni en suc_d antes de llegar
--from remision r,sucursal s,estado e,empleado emp,detalle_rem d						-->ver para agregar suc_d a esta vista, posible necesidad de utilizar DISTINCT
--where s.suc_cod=r.suc_cod_o and e.est_cod=r.est_cod and emp.emp_ci=r.emp_ci);
-------------------------------------------------------------------------------------------------drem
--create or replace view v_drem as(select m.mat_des,d.dep_des as _o,d.dep_des,dd.det_cant,r.rem_cod 
--from detalle_rem dd,remision r,material m,deposito d
--where dd.dep_cod=d.dep_cod and dd.dep_cod_o=d.dep_cod and m.mat_cod=dd.mat_cod);
-------------------------------------------------------------------------------------------------rem_R
--create or replace view v_remr as(select r.rem_cod,s.suc_cod as suc_o,s.suc_des,r.rem_date,r.rem_null,r.emp_ci 
--from remision_r r,sucursal s,detalle_rem_r d
--where s.suc_cod=r.suc_cod_o and s.suc_cod=d.suc_cod and r.rem_cod=d.rem_cod);
-------------------------------------------------------------------------------------------------drem_R
--create or replace view v_dremr as(select m.rep_des,d.dep_des as _o,d.dep_des,dd.det_cant,r.rem_cod 
--from detalle_rem_r dd,remision_r r,repuesto m,deposito d
--where dd.dep_cod=d.dep_cod and dd.dep_cod_o=d.dep_cod and m.rep_cod=dd.rep_cod);
-------------------------------------------------------------------------------------------------det_ajum		
create or replace view v_dajum as(SELECT m.mat_des,
    d.dep_des,
    s.suc_des,
    dd.cant,
    concat(m.mat_cod, ' - ', m.mat_des) AS mat,
    concat(d.dep_cod, ' - ', d.dep_des) AS dep,
    concat(s.suc_cod, ' - ', s.suc_des) AS suc,
    concat(mo.mot_cod, ' - ', mo.mot_des) AS mot,
    dd.aju_cod,
    dd.tmot_cod
FROM material m,
    deposito d,
    detalle_ajuste_m dd,
    sucursal s,
    ajuste_m a,
    motivo_ajuste mo
WHERE m.mat_cod = dd.mat_cod AND s.suc_cod = dd.suc_cod AND d.dep_cod = dd.dep_cod AND a.aju_cod = dd.aju_cod and mo.mot_cod=dd.mot_cod);
-------------------------------------------------------------------------------------------------ajum
create or replace view v_ajum as(select --select * from ajuste_m
a.aju_cod,
a.aju_date,
d.suc_des,
emp.emp_user,
to_char(a.aju_date,'DD/MM/YYYY'),
e.est_des
 from ajuste_m a,v_dajum d,estado e,empleado emp 
 where d.aju_cod=a.aju_cod and e.est_cod=a.est_cod and emp.emp_ci=a.emp_ci);
 -------------------------------------------------------------------------------------------------det_ajur
create or replace view v_dajur as(SELECT
  m.rep_des,
  d.dep_des,
  s.suc_des,
  dd.cant,
  CONCAT(m.rep_cod,' - ',m.rep_des) AS mat,
  CONCAT(d.dep_cod,' - ',d.dep_des) AS dep,
  CONCAT(s.suc_cod,' - ',s.suc_des) AS suc,
  dd.aju_cod
from repuesto m,deposito d,detalle_ajuste_r dd,sucursal s,ajuste_r a
where m.rep_cod=dd.rep_cod and s.suc_cod=dd.suc_cod and d.dep_cod=dd.dep_cod and a.aju_cod=dd.aju_cod);
-------------------------------------------------------------------------------------------------ajur
create or replace view v_ajur as(select a.aju_cod,a.aju_date,d.suc_des,emp.emp_user,to_char(a.aju_date,'DD/MM/YYYY')
 from ajuste_r a,v_dajur d,estado e,empleado emp 
 where d.aju_cod=a.aju_cod and e.est_cod=a.est_cod and emp.emp_ci=a.emp_ci);
-------------------------------------------------------------------------------------------------ordc--
--drop view v_ordc;
create or replace view v_ordc as (SELECT
  o.ord_nro,
  (select p.ped_cod from v_ped p where p.ped_cod=o.ped_cod),
  CONCAT(o.prov_cod,' - ',p.prov_des) AS prov,
  concat(t.tip_cod,' - ',t.tip_des) as tip,
  to_char(o.ord_date,'DD/MM/YYYY') AS date_,
  e.est_des,
  o.ord_date,
  CONCAT(s.suc_cod,' - ',s.suc_des) AS suc,
 -- (select concat(suc_cod,' ',suc_des) from v_ped p where p.ped_cod=o.ped_cod) as suc,
  o.emp_ci,
  o.suc_cod,
  t.tip_des,
  s.suc_des
 -- p.prov_cod,
  --p.prov_des
FROM ord_compra o,proveedor p,tipo_de_pago t,sucursal s,estado e
WHERE p.prov_cod = o.prov_cod AND t.tip_cod = o.tip_cod and s.suc_cod=o.suc_cod and e.est_cod=o.est_cod);
-------------------------------------------------------------------------------------------------com
--drop view v_com
create or replace view v_com as(SELECT--select * from compra
  c.com_cod,
  --CONCAT(p.prov_cod,' - ',p.prov_des) AS prov,
 (select x.ped_cod from v_ordc x where x.ord_nro=c.ord_nro),
 (select x.ord_nro from v_ordc x where x.ord_nro=c.ord_nro),
 (select prov from v_ordc x where x.ord_nro=c.ord_nro),
  --c.ord_nro,
  c.com_nrofact,
  c.com_date,--
  e.est_des,
  to_char(c.com_date,'DD/MM/YYYY'),
  c.emp_ci,
  c.suc_cod
FROM compra c,estado e
WHERE e.est_cod=c.est_cod);
-------------------------------------------------------------------------------------------------det_com
create or replace view v_dcom as(SELECT
  d.com_cant   AS com_cant,
  m.mat_des  AS mat_des,
  d.com_precio AS com_precio,
  (d.com_precio * d.com_cant) AS total,
  c.com_cod    AS com_cod,
  d.mat_cod    AS mat_cod,
  d.dep_cod    AS dep_cod,
  d.suc_cod    AS suc_cod,
  concat(dd.dep_cod,' - ',dd.dep_des)as dep,
  concat(m.mat_cod,' - ',m.mat_des)as mat,
  concat(s.suc_cod,' - ',s.suc_des)as suc,
  s.suc_des
FROM detalle_com d,material m,compra c,deposito dd,sucursal s
WHERE m.mat_cod = d.mat_cod AND c.com_cod = d.com_cod and dd.dep_cod=d.dep_cod and s.suc_cod=d.suc_cod);
-------------------------------------------------------------------------------------------------comr
create or replace view v_comr as(SELECT
  c.com_cod,
  CONCAT(p.prov_cod,' - ',p.prov_des) AS prov,
  o.ped_cod,
  c.ord_cod,
  c.com_nrofact,
  c.com_date,
  e.est_des,
  to_char(c.com_date,'DD/MM/YYYY'),
  c.emp_ci,
  c.suc_cod
FROM compra_r c,proveedor p,ord_compra_r o,estado e
WHERE p.prov_cod = o.prov_cod AND o.ord_cod = c.ord_cod and e.est_cod=c.est_cod);
-------------------------------------------------------------------------------------------------det_comr
create or replace view v_dcomr as(SELECT
  d.com_cant   AS com_cant,
  m.rep_des  AS rep_des,
  d.com_precio AS com_precio,
  (d.com_precio * d.com_cant) AS total,
  c.com_cod    AS com_cod,
  d.rep_cod    AS rep_cod,
  d.dep_cod    AS dep_cod,
  d.suc_cod    AS suc_cod
FROM detalle_com_r d,repuesto m,compra_r c
WHERE m.rep_cod = d.rep_cod AND c.com_cod = d.com_cod);
-------------------------------------------------------------------------------------------------dordcr    
create or replace view v_dordcr as (SELECT
  d.det_cant   AS det_cant,
  t.trep_des   AS trep_des,
  m.rep_des    AS rep_des,
  d.det_precio AS det_precio,
  (d.det_precio * d.det_cant) AS total,
  d.ord_cod    AS ord_cod,
  o.ped_cod    AS ped_cod,
  d.rep_cod    AS rep_cod
FROM detalle_ord_compra_r d,repuesto m,tipo_rep t,ord_compra_r o
WHERE m.rep_cod = d.rep_cod AND t.trep_cod = m.trep_cod and d.ord_cod=o.ord_cod);
-------------------------------------------------------------------------------------------------dordc
CREATE OR REPLACE VIEW v_dordc AS (
 SELECT d.det_cant,
    m.un_cod,
    m.mat_des,
    d.det_precio,
    d.det_precio::numeric * d.det_cant AS total,
    d.ord_nro,
    o.ped_cod,
    d.mat_cod,
    concat(m.mat_cod,' - ',m.mat_des)as mat
   FROM detalle_ord_compra d,ord_compra o,
    material m
  WHERE m.mat_cod = d.mat_cod and o.ord_nro=d.ord_nro and d.ord_nro=o.ord_nro);
-------------------------------------------------------------------------------------------------det_ped
create or replace view v_dped as(SELECT
  d.det_cant ,
  u.un_cod   ,
  CONCAT(m.mat_cod,' - ',m.mat_des) AS mat,
  e.est_des ,
  d.det_ord  ,
  d.ped_cod  ,
  m.mat_cod,
  m.mat_des
FROM detalle_ped d,pedido p,material m,un_de_medida u,estado e
WHERE d.ped_cod = p.ped_cod AND m.mat_cod = d.mat_cod AND u.un_cod = m.un_cod and e.est_cod=d.est_cod);
-------------------------------------------------------------------------------------------------det_pedR
create or replace view v_dpedr as(SELECT
  d.det_cant,
  u.trep_des,
  CONCAT(m.rep_cod,' - ',m.rep_des) AS rep,
  e.est_des,
  d.det_ord,
  d.ped_cod
FROM detalle_ped_r d,pedido_r p,repuesto m,tipo_rep u,estado e
WHERE d.ped_cod = p.ped_cod AND m.rep_cod = d.rep_cod AND u.trep_cod = m.trep_cod and e.est_cod=d.est_cod);
-------------------------------------------------------------------------------------------------dprep1
create or replace view v_dprep1 as(SELECT
  r.rep_des  AS rep_des,
  d.det_org  AS det_org,
  d.det_pre  AS det_pre,
  d.prep_cod AS prep_cod
FROM repuesto r,detalle_prep_rep d
WHERE r.rep_cod = d.rep_cod); 
-------------------------------------------------------------------------------------------------dprep2
create or replace view v_dprep2 as (SELECT
  p.pie_des    AS pie_des,
  da.dam_des   AS dam_des,
  d.det_precio AS det_precio,
  d.prep_cod   AS prep_cod
FROM pieza p,damage da,detalle_prep_pzas d
WHERE p.pie_cod = d.pie_cod AND da.dam_cod =d.dam_cod);
-------------------------------------------------------------------------------------------------dprep3
create or replace view v_dprep3 as (SELECT
  t.trab_des AS trab_des,
  d.det_pre  AS det_pre,
  d.prep_cod AS prep_cod
FROM trabajos_terciarios t,detalle_prep_trab d,presupuesto p
WHERE t.trab_cod = d.trab_cod AND d.prep_cod = p.prep_cod);
-------------------------------------------------------------------------------------------------ent
create or replace view v_ent as(SELECT
  e.ent_cod ,
  e.prep_cod,
  s.suc_des ,
  CONCAT('(',v.veh_cod,')',v.veh_cha,' - ',v.veh_chas,' - ',m.mar_des,', ',mo.mod_des,' - ',co.col_des) AS veh,
  ss.seg_des,
  CONCAT(cl.cli_ci,' - ',cl.cli_nom,', ',cl.cli_ape) AS cli,
  c.comb_des,
  e.ent_date,
  e.ent_otros ,
  e.ent_km ,
  e.ent_key,
  e.ent_cat,
  e.ent_bal,
  e.ent_aux,
  e.ent_taz,
  e.ent_her,
  e.ent_rad ,
  e.ent_eq,
  e.ent_ext,
  e.ent_gar,
  es.est_des,
  e.emp_ci
FROM entrada e,sucursal s,vehiculo v,seguro ss,cliente cl,combustible c,detalle_veh d,marca m,modelo mo,color co,estado es
WHERE s.suc_cod = e.suc_cod AND v.veh_cod = d.veh_cod AND cl.cli_ci = d.cli_ci AND v.veh_cod = e.veh_cod
       AND cl.cli_ci = e.cli_ci AND ss.seg_cod = e.seg_cod AND c.comb_cod = e.comb_cod AND m.mar_cod = v.mar_cod
       AND mo.mod_cod = v.mod_cod AND co.col_cod = v.col_cod AND es.est_cod=e.est_cod);
--*******************************************************************************--
--*******************************************************************************--
-------------------------------------------------------------------------------------------------log
create or replace view v_log as (SELECT
  e.emp_ci,
  e.emp_user,
  e.emp_pass,
  s.suc_des,
  e.car_cod,
  es.est_des,
  CONCAT(e.emp_ci,' - ',e.emp_nom,', ',e.emp_ape) AS emp,
  c.car_user,
  c.car_pass,
  e.emp_block,
  s.suc_cod 
FROM empleado e,sucursal s,cargo c,detalle_emp d,estado es
WHERE s.suc_cod = d.suc_cod AND c.car_cod = e.car_cod and d.emp_ci=e.emp_ci and d.date_egr isnull and es.est_cod=e.est_cod);
--*******************************************************************************--
--*******************************************************************************--
-------------------------------------------------------------------------------------------------ped
create or replace view v_ped as (SELECT
  p.ped_cod  ,
  s.suc_des  ,
  to_char(p.ped_datetime,'DD/MM/YYYY HH:MM:SS') AS date_,
  concat(e.emp_ci,' - ',e.emp_user)as emp,
  es.est_des ,
  p.suc_cod     ,
  p.ped_datetime,
  p.emp_ci
FROM pedido p,sucursal s,empleado e,estado es
WHERE s.suc_cod = p.suc_cod and e.emp_ci=p.emp_ci and es.est_cod=p.est_cod);
-------------------------------------------------------------------------------------------------pedr
create or replace view v_pedr as (SELECT
  p.ped_cod,
  s.suc_des,
  to_char(p.ped_datetime,'DD/MM/YYYY HH:MM:SS') AS date_,
  concat(e.emp_ci,' - ',e.emp_user)as emp,
  es.est_des,
  p.suc_cod,
  p.ped_datetime,
  p.emp_ci
FROM pedido_r p,sucursal s,empleado e,estado es
WHERE s.suc_cod = p.suc_cod and e.emp_ci=p.emp_ci and es.est_cod=p.est_cod);

-------------------------------------------------------------------------------------------------ordcr
create or replace view v_ordcr as(SELECT
  o.ord_cod,
  o.ped_cod,
  CONCAT(o.prov_cod,' - ',p.prov_des) AS prov,
  t.tip_des,
  to_char(o.ord_date,'DD/MM/YYYY') AS date_,
  e.est_cod,
  o.ord_date,
  CONCAT(pp.suc_cod,' - ',pp.suc_des) AS suc,
  o.emp_ci
FROM ord_compra_r o,proveedor p,tipo_de_pago t,v_pedr pp,estado e
WHERE p.prov_cod = o.prov_cod AND t.tip_cod = o.tip_cod AND pp.ped_cod = o.ped_cod and e.est_cod=o.est_cod);
-------------------------------------------------------------------------------------------------stock_M
create or replace view v_stock_m as(SELECT--select * from stock_mat
  m.mat_des AS mat_des,
  s.suc_des AS suc_des,
  d.dep_des AS dep_des,
  st.cant   AS cant,
  m.mat_min AS mat_min,
  m.un_cod  AS un_cod,
  m.mat_cod AS mat_cod,
  concat(d.dep_cod,' - ',d.dep_des)as dep,
  s.suc_cod,
  d.dep_cod,
  st.cant_no_disp
FROM material m,sucursal s,deposito d,stock_mat st
WHERE m.mat_cod = st.mat_cod AND s.suc_cod = st.suc_cod AND d.dep_cod = st.dep_cod AND (st.cant > 0.00 OR st.cant_no_disp >0.00));
-------------------------------------------------------------------------------------------------stock_R
create or replace view v_stock_r as (SELECT
  m.rep_des  AS rep_des,
  s.suc_des  AS suc_des,
  d.dep_des  AS dep_des,
  st.cant    AS cant,
  t.trep_des AS trep_des
FROM repuesto m,sucursal s,deposito d,stock_rep st,tipo_rep t
WHERE m.rep_cod = st.rep_cod AND s.suc_cod = st.suc_cod AND d.dep_cod = st.suc_cod AND t.trep_cod = m.trep_cod AND st.cant > 0.0);
-------------------------------------------------------------------------------------------------rem
--drop view v_rem
create or replace view v_rem as(SELECT--select * from remision
r.rem_cod,
s.suc_des,
concat(s.suc_cod,' - ',s.suc_des)as suc,
ee.est_des,
e.emp_ci,
e.emp_user,
r.rem_date
FROM remision r,sucursal s,empleado e,estado ee
WHERE s.suc_cod=r.suc_cod and e.emp_ci=r.emp_ci and ee.est_cod=r.est_cod );
-------------------------------------------------------------------------------------------------drem
--drop view v_drem
create or replace view v_drem as(SELECT--select * from detalle_rem
dd.det_cant,--cant
m.mat_des,--mat
s.suc_des,--sucursal destino
d.dep_des,--deposito destino
--ss.suc_des as suc_o,--sucursal origen
--ddd.dep_des as dep_o,--deposito origen
concat(m.mat_cod,' - ',m.mat_des)as mat,
concat(s.suc_cod,' - ',s.suc_des)as suc,
concat(d.dep_cod,' - ',d.dep_des)as dep,
concat(ddd.dep_cod,' - ',ddd.dep_des)as dep_o,
r.rem_cod,
dd.suc_cod,
dd.dep_cod,
m.mat_cod,
dd.suc_cod_o,
dd.dep_cod_o
FROM remision r,sucursal s,detalle_rem dd,deposito d,material m,deposito ddd
WHERE s.suc_cod=dd.suc_cod and d.dep_cod=dd.dep_cod and m.mat_cod=dd.mat_cod and ddd.dep_cod=dd.dep_cod_o);
