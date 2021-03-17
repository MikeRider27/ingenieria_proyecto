-------------------------------------------------------------------------------------------------ban
create or replace view v_ban as(SELECT * from banco);
-------------------------------------------------------------------------------------------------mot
create or replace view v_mot as(select m.mot_cod,t.tmot_des,m.mot_des,m.est_cod,m.tmot_cod
from motivo_ajuste m,tipo_mot_aju t
where t.tmot_cod=m.tmot_cod);
-------------------------------------------------------------------------------------------------motg
create or replace view v_motg as(SELECT * from motivo_garantia);
-------------------------------------------------------------------------------------------------car
create or replace view v_car as(SELECT * FROM cargo WHERE car_cod!=0);
-------------------------------------------------------------------------------------------------ciu
create or replace view v_ciu as(SELECT * from ciudad);
-------------------------------------------------------------------------------------------------col
create or replace view v_col as(SELECT * from color);
-------------------------------------------------------------------------------------------------est
create or replace view v_est as(SELECT * from estado);
-------------------------------------------------------------------------------------------------tmot
create or replace view v_tmot as(SELECT * from tipo_mot_aju);
-------------------------------------------------------------------------------------------------comb
create or replace view v_comb as(SELECT * from combustible);
-------------------------------------------------------------------------------------------------dam
create or replace view v_dam as(SELECT * from damage);
-------------------------------------------------------------------------------------------------eta
create or replace view v_eta as(SELECT * from etapa);
-------------------------------------------------------------------------------------------------mar
create or replace view v_mar as(SELECT * from marca);
-------------------------------------------------------------------------------------------------mat
create or replace view v_mat as(SELECT * from material);
-------------------------------------------------------------------------------------------------det_mat
--create or replace view v_dmat2 as();
-------------------------------------------------------------------------------------------------param_mat
create or replace view v_dmat as(SELECT dd.dam_des,m.mat_des,d.cant,d.est_cod,d.mat_cod,concat(dd.dam_cod,' - ',dd.dam_des)as dam
from material m,damage dd,detalle_mat d
where m.mat_cod=d.mat_cod and dd.dam_cod=d.dam_cod);
-------------------------------------------------------------------------------------------------nac
create or replace view v_nac as(SELECT * from nacionalidad);
-------------------------------------------------------------------------------------------------prov
create or replace view v_prov as(SELECT * from proveedor);
-------------------------------------------------------------------------------------------------provs
create or replace view v_provs as(SELECT * from proveedor_serv);
-------------------------------------------------------------------------------------------------det_prov
--drop view v_dprovs
create or replace view v_dprovs as (SELECT
  t.trab_des,
  concat(t.trab_cod,' - ',t.trab_des)as trab,
  p.prov_cod,
  d.est_cod
FROM trabajos_terciarios t,proveedor_serv p,detalle_prov d
WHERE p.prov_cod = d.prov_cod AND t.trab_cod = d.trab_cod);
-------------------------------------------------------------------------------------------------pza
create or replace view v_pzas as(SELECT p.pie_cod,p.est_cod,p.pie_des,p.pie_cant,t.tveh_des,t.tveh_cod
from pieza p,tipo_veh t
where t.tveh_cod=p.tveh_cod);
-------------------------------------------------------------------------------------------------det_pza
--create or replace view v_dpzas as(SELECT * from detalle_pieza);
-------------------------------------------------------------------------------------------------seg
create or replace view v_seg as(SELECT * from seguro);
-------------------------------------------------------------------------------------------------suc
create or replace view v_suc as(SELECT * from sucursal);
-------------------------------------------------------------------------------------------------tpag
create or replace view v_tpag as(SELECT * from tipo_de_pago);
-------------------------------------------------------------------------------------------------trab
create or replace view v_trab as(SELECT * from trabajos_terciarios);
-------------------------------------------------------------------------------------------------tveh
create or replace view v_tveh as(SELECT *,concat(tveh_cod,' - ',tveh_des)as tveh
from tipo_veh);
-------------------------------------------------------------------------------------------------un
create or replace view v_un as(SELECT * from un_de_medida);
-------------------------------------------------------------------------------------------------caj
create or replace view v_caj as(SELECT * from caja);
-------------------------------------------------------------------------------------------------per
create or replace view v_per as(SELECT * from permiso);
-------------------------------------------------------------------------------------------------modulo
create or replace view v_modulo as(SELECT * from modulo);
-------------------------------------------------------------------------------------------------impuesto
--create or replace view v_imp as(SELECT * from impuesto);
-------------------------------------------------------------------------------------------------rol
create or replace view v_rol as(SELECT * from rol);
-------------------------------------------------------------------------------------------------det_rol
create or replace view v_drol as(SELECT d.rol_cod,g.gui_des,p.per_des,d.gui_cod,p.per_cod
from detalle_rol d,gui g,permiso p
where g.gui_cod=d.gui_cod and p.per_cod=d.per_cod);
-------------------------------------------------------------------------------------------------gui
create or replace view v_gui as(SELECT g.gui_cod,g.est_cod,m.mod_des,g.gui_des
from gui g,modulo m
where g.mod_cod=m.mod_cod);
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
v.est_cod,
concat(m.mar_cod,' - ',m.mar_des)as mar,
concat(mo.mod_cod,' - ',mo.mod_des)as mod_,
concat(c.col_cod,' - ',c.col_des)as col,
concat(t.tveh_cod,' - ',t.tveh_des)as tveh,
concat(v.veh_cod,' - ',m.mar_des,', ',mo.mod_des,' ',c.col_des,' ',v.veh_año)as veh
from vehiculo v,marca m,modelo mo,tipo_veh t,color c
where m.mar_cod=v.mar_cod and mo.mod_cod=v.mod_cod and t.tveh_cod=v.tveh_cod and c.col_cod=v.col_cod);
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
  b.est_cod
FROM barrio b,ciudad c
WHERE c.ciu_cod=b.ciu_cod);
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
c.est_cod,
to_char(c.cli_fnac,'DD/MM/YYYY'),
concat(ci.ciu_cod,' - ',ci.ciu_des)as ciu,
concat(b.bar_cod,' - ',b.bar_des)as bar,
concat(n.nac_cod,' - ',n.nac_des)as nac,
concat(c.cli_ci,' - ',c.cli_nom,', ',c.cli_ape)as cli
from cliente c,nacionalidad n,ciudad ci,barrio b
where n.nac_cod=c.nac_cod and c.ciu_cod=ci.ciu_cod and b.bar_cod=c.bar_cod);
--SELECT date_part('year',age(birth)),* FROM persons LIMIT 1;
-------------------------------------------------------------------------------------------------mod
create or replace view v_mod as (SELECT
  m.mod_cod,
  ma.mar_des,
  m.mod_des,
  m.est_cod,
  CONCAT(ma.mar_cod,' - ',ma.mar_des) AS mar,
  CONCAT(m.mod_cod,' - ',m.mod_des) AS mod_
FROM modelo m,marca ma
WHERE ma.mar_cod = m.mar_cod);
-------------------------------------------------------------------------------------------------det_mod
create or replace view v_dmod as(SELECT
  m.mar_des,
  mo.mod_des,
  t.tveh_des,
  t.tveh_cod,
  concat(t.tveh_cod,' - ',t.tveh_des)as tveh,
  d.est_cod,
  mo.mod_cod,
  m.mar_cod
FROM marca m,modelo mo,tipo_veh t,detalle_modelo d
WHERE m.mar_cod = d.mar_cod AND mo.mod_cod = d.mod_cod AND t.tveh_cod = d.tveh_cod);
-------------------------------------------------------------------------------------------------tim
create or replace view v_tim as (select *,to_char(tim_venc,'DD/MM/YYYY')from timbrado);
-------------------------------------------------------------------------------------------------dep
create or replace view v_dep as(SELECT
  d.dep_cod ,
  CONCAT(s.suc_cod,' - ',s.suc_des) AS suc,
  d.dep_des ,
  d.est_cod,
  s.suc_des 
FROM deposito d,sucursal s
WHERE s.suc_cod = d.suc_cod);
-------------------------------------------------------------------------------------------------usr
create or replace view v_usr as(SELECT
  u.usr_cod,
  CONCAT(u.usr_cod,' - ',u.usr_des) AS usr,
  u.usr_des,
  u.usr_pass,
  u.est_cod
FROM usuario u
WHERE u.usr_cod!=0);
-------------------------------------------------------------------------------------------------det_usr
create or replace view v_dusr as(SELECT d.usr_cod,r.rol_des,d.rol_cod
from detalle_usuario d, rol r
where r.rol_cod=d.rol_cod);
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
create or replace view v_func as (SELECT --select * from empleado
  e.emp_ci ,
  n.nac_des,
  CONCAT(c.car_cod,' - ',c.car_des) AS car,
  e.emp_nom,
  e.emp_ape,
  e.emp_cel,
  e.emp_dir,
  CONCAT(n.nac_cod,' - ',n.nac_des) AS nac,
  CONCAT(d.suc_cod,' - ',d.suc_des) AS suc,
  (select u.usr from v_usr u where u.usr_cod=e.usr_cod)as usr_full,
  (select u.usr_des from v_usr u where u.usr_cod=e.usr_cod)as usr,
  e.emp_fnac,
  d.date_ing,
  d.date_egr,
  e.usr_cod,
  to_char(e.emp_fnac,'DD/MM/YYYY'),
  e.est_cod
FROM empleado e,nacionalidad n,cargo c,v_demp d
WHERE n.nac_cod = e.nac_cod AND c.car_cod = e.car_cod and d.emp_ci=e.emp_ci and d.date_egr isnull);
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
    dd.tmot_cod,
    dd.mat_cod,
    dd.mot_cod,
    dd.dep_cod
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
emp.usr,
to_char(a.aju_date,'DD/MM/YYYY'),
a.est_cod
 from ajuste_m a,v_dajum d,v_func emp
 where d.aju_cod=a.aju_cod and emp.emp_ci=a.emp_ci);
-------------------------------------------------------------------------------------------------ped
create or replace view v_ped as (SELECT
  p.ped_cod  ,
  s.suc_des  ,
  to_char(p.ped_datetime,'DD/MM/YYYY HH:MM:SS') AS date_,
  concat(e.emp_ci,' - ',e.emp_nom)as emp,
  p.est_cod ,
  p.suc_cod     ,
  p.ped_datetime,
  p.emp_ci
FROM pedido p,sucursal s,empleado e
WHERE s.suc_cod = p.suc_cod and e.emp_ci=p.emp_ci);
-------------------------------------------------------------------------------------------------ordc--
--drop view v_ordc;
create or replace view v_ordc as (SELECT
  o.ord_nro,
  (select p.ped_cod from v_ped p where p.ped_cod=o.ped_cod),
  CONCAT(o.prov_cod,' - ',p.prov_des) AS prov,
  concat(t.tip_cod,' - ',t.tip_des) as tip,
  to_char(o.ord_date,'DD/MM/YYYY') AS date_,
  o.est_cod,
  o.ord_date,
  CONCAT(s.suc_cod,' - ',s.suc_des) AS suc,
 -- (select concat(suc_cod,' ',suc_des) from v_ped p where p.ped_cod=o.ped_cod) as suc,
  o.emp_ci,
  o.suc_cod,
  t.tip_des,
  s.suc_des
 -- p.prov_cod,
  --p.prov_des
FROM ord_compra o,proveedor p,tipo_de_pago t,sucursal s
WHERE p.prov_cod = o.prov_cod AND t.tip_cod = o.tip_cod and s.suc_cod=o.suc_cod);
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
  c.est_cod,
  to_char(c.com_date,'DD/MM/YYYY'),
  c.emp_ci,
  c.suc_cod
FROM compra c);
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
  d.est_cod ,
  d.det_ord  ,
  d.ped_cod  ,
  m.mat_cod,
  m.mat_des
FROM detalle_ped d,pedido p,material m,un_de_medida u
WHERE d.ped_cod = p.ped_cod AND m.mat_cod = d.mat_cod AND u.un_cod = m.un_cod);
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
  u.usr_des,--sys usr
  u.usr_pass,--sys pass
  s.suc_des,
  e.car_cod,
  e.est_cod as est_emp,
  CONCAT(e.emp_ci,' - ',e.emp_nom,', ',e.emp_ape) AS emp,
 -- c.car_user,--db usr
 -- c.car_pass,--db pass
  s.suc_cod 
FROM empleado e,sucursal s,cargo c,detalle_emp d,usuario u
WHERE s.suc_cod = d.suc_cod AND c.car_cod = e.car_cod and d.emp_ci=e.emp_ci and d.date_egr isnull and u.usr_cod=e.usr_cod);
--*******************************************************************************--
--*******************************************************************************--
-------------------------------------------------------------------------------------------------stock_M
create or replace view v_stock_m as(SELECT
  m.mat_des AS mat_des,
  s.suc_des AS suc_des,
  d.dep_des AS dep_des,
  st.cant   AS cant,
  dm.mat_min AS mat_min,
  m.un_cod  AS un_cod,
  m.mat_cod AS mat_cod,
  concat(d.dep_cod,' - ',d.dep_des)as dep,
  s.suc_cod,
  d.dep_cod,
  st.cant_no_disp
FROM material m,sucursal s,deposito d,stock_mat st,detalle_material dm
WHERE m.mat_cod = st.mat_cod AND s.suc_cod = st.suc_cod AND d.dep_cod = st.dep_cod AND (st.cant > 0.00 OR st.cant_no_disp >0.00) and dm.mat_cod=m.mat_cod and s.suc_cod=dm.suc_cod);
-------------------------------------------------------------------------------------------------rem
--drop view v_rem
create or replace view v_rem as(SELECT--select * from remision
r.rem_cod,
s.suc_des,
concat(s.suc_cod,' - ',s.suc_des)as suc,
r.est_cod,
e.emp_ci,
u.usr_des,
r.rem_date
FROM remision r,sucursal s,empleado e,usuario u
WHERE s.suc_cod=r.suc_cod and e.emp_ci=r.emp_ci and u.usr_cod=e.usr_cod);
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
