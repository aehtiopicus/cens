insert into cens_usuarios (id,enabled,password,username) values(-1,true,'dcwfDxz853Odd7yALiiZV0vlgA3nIBdV','censadmin');

insert into cens_perfil_usuario_cens (id,perfiltype,usuario_id) values(-3,'ROLE_ASESOR',-1);
insert into cens_perfil_usuario_cens (id,perfiltype,usuario_id) values(-2,'ROLE_PROFESOR',-1);
insert into cens_perfil_usuario_cens (id,perfiltype,usuario_id) values(-1,'ROLE_PRECEPTOR',-1);
insert into cens_perfil_usuario_cens (id,perfiltype,usuario_id) values(0,'ROLE_ADMINISTRADOR',-1);

insert into cens_miembros_cens(id,apellido,baja,dni,fechanac,nombre,usuario_id) values(-1,'censadmin',false,'99999999','1900-01-01','censadmin',-1);

insert into cens_asesor (id,baja,miembrocens_id) values(-1,false,-1);
insert into cens_profesor (id,baja,miembrocens_id) values(-1,false,-1);
insert into cens_preceptor (id,baja,miembrocens_id) values(-1,false,-1);

insert into cens_scheduler_jobs (id,job_name,job_sec,job_min,job_hour,job_day,job_month,job_enabled,job_modify) 
values(1,'un_read_job','*','10','*','*','*',false,false);
insert into cens_scheduler_jobs (id,job_name,job_sec,job_min,job_hour,job_day,job_month,job_enabled,job_modify) 
values(2,'general_notification_job','10','*','*','*','*',false,false);
insert into cens_scheduler_jobs (id,job_name,job_sec,job_min,job_hour,job_day,job_month,job_enabled,job_modify) 
values(3,'token_fb','10','*','*','*','*',false,false);
insert into cens_scheduler_jobs (id,job_name,job_sec,job_min,job_hour,job_day,job_month,job_enabled,job_modify) 
values(4,'tiempo_edicion','10','*','*','*','*',false,false);
