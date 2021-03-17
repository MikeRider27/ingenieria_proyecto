create schema read_only;
create role read_only login encrypted password 'qwerty';
grant connect on taller to read_only;
create or replace view read_only.v_log as (SELECT
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
GRANT SELECT ON read_only.v_log TO read_only;
GRANT USAGE ON SCHEMA read_only TO read_only;

https://www.scriptscribe.org/databases/granting-permissions-in-postgres-compared-to-mysql/
http://www.postgresql.org/docs/9.4/static/sql-grant.html