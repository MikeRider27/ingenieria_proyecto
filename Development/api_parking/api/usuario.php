<?php
session_start();
include('../core/connection.php');
$dbconn = getConnection();

// Check if the form was sent and the action is SAVE
if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "insert") {
    //terminado
    $result = 0;
    $id_usuario = 0;
    $nick = mb_strtoupper(trim($_REQUEST['nick']), 'UTF-8');
    $nombre = mb_strtoupper(trim($_REQUEST['nombre']), 'UTF-8');
    $email = $_REQUEST['email'];
    $pass = sha1($_REQUEST['pass']);


    try {
        $stmt2 = $dbconn->prepare("SELECT * FROM usuario WHERE nick = :nick");
        $stmt2->bindValue(":nick", $nick);
        $stmt2->execute();
        $usuario = $stmt2->fetch(PDO::FETCH_ASSOC);
        if (!empty($usuario['nick'])) {
            $message = "Ya se encuentra registrado un usuario con ese nick = " . $nick;
            $status = "error";
            print json_encode(array("status" => $status, "message" => $message));
            exit();
        } else {
            $sql = 'INSERT INTO usuario (nick, nombre, email, pass)
					VALUES (:nick, :nombre, :email, :pass)';
            $stmt = $dbconn->prepare($sql);
            $stmt->bindValue(':nick', $nick);
            $stmt->bindValue(':nombre', $nombre);
            $stmt->bindValue(':email', $email);
            $stmt->bindValue(':pass', $pass);
            $result = $stmt->execute();
            $id_usuario = $dbconn->lastInsertId();
            $message = $result ? "Se registró correctamente el usuario con el id = " . $id_usuario :
                "Ocurrio un error intentado resolver la solicitud";
            $status = $result ? "success" : "error";
            print json_encode(array("status" => $status, "message" => $message, "id_usuario" => $id_usuario));
        }
    } catch (Exception $e) {
        $result = FALSE;
        var_dump($e->getMessage());
    }
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "update") {
    //terminado
    $result = 0;
    $id_usuario = $_REQUEST['id_usuario'];
    $nick = mb_strtoupper(trim($_REQUEST['nick']), 'UTF-8');
    $nombre = mb_strtoupper(trim($_REQUEST['nombre']), 'UTF-8');
    $email = $_REQUEST['email'];
    $pass = sha1($_REQUEST['pass']);
    if (!empty($id_usuario)) {
        $sql = 'UPDATE usuario SET nick= :nick,
                                             nombre= :nombre, 
                                             email= :email,
                                             pass= :pass WHERE id_usuario = :id_usuario';
        $stmt = $dbconn->prepare($sql);
        $stmt->bindValue(':id_usuario', $id_usuario);
    }
    $stmt->bindValue(':nick', $nick);
    $stmt->bindValue(':nombre', $nombre);
    $stmt->bindValue(':email', $email);
    $stmt->bindValue(':pass', $pass);
    $result = $stmt->execute();
    $message = $result ? "Registro del usuario modificado exitosamente!" : "Ocurrio un error intentado resolver la solicitud, por favor complete todos los campos o recargue de vuelta la pagina";
    $status = $result ? "success" : "error";
    print json_encode(array("status" => $status, "message" => $message));
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "delete") {
    //terminado
    $result = 0;
    $id_usuario = $_REQUEST['id_usuario'];
    if (!empty($id_usuario)) {
        $sql = 'DELETE FROM usuario WHERE id_usuario = :id_usuario';
        $stmt = $dbconn->prepare($sql);
        $stmt->bindValue(':id_usuario', $id_usuario);
    }
    $result = $stmt->execute();
    $message = $result ? "Registro del usuario borrado exitosamente!" : "Ocurrio un error intentado resolver la solicitud, por favor complete todos los campos o recargue de vuelta la pagina";
    $status = $result ? "success" : "error";
    print json_encode(array("status" => $status, "message" => $message));
} else if (isset($_GET['accion']) and $_GET['accion'] == "select") {
    // funciona el select para la grilla
    $stmt = $dbconn->prepare('SELECT * FROM usuario ORDER BY id_usuario ASC');
    $stmt->execute();
    $data = array();
    while ($usuario = $stmt->fetch(PDO::FETCH_OBJ)) {
        $data[] = array(
            "id_usuario" => $usuario->id_usuario,
            "nick" => $usuario->nick,
            "nombre" => $usuario->nombre,
            "email" => $usuario->email
        );
    }
    echo json_encode($data);
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "search") {
    //terminado
    $id_usuario = $_REQUEST['id_usuario'];

    $sql_search = "SELECT * from usuario where id_usuario=:id_usuario";

    $stmt = $dbconn->prepare($sql_search);
    //poner numero de cantidad de campos por tabla diferente cantidad 
    $stmt->bindParam(':id_usuario', $id_usuario);

    if ($stmt->execute()) {
        $a = 0;
        while ($usuario = $stmt->fetch(PDO::FETCH_OBJ)) {
            $datos = array(
                "status" => "success",
                "id_usuario" => $usuario->id_usuario,
                "nick" => $usuario->nick,
                "nombre" => $usuario->nombre,
                "email" => $usuario->email
            );
            $a = 1;
        }

        if ($a == 0) $datos = array("status" => "NO EXISTE");
    } else {
        $datos = array("status" => "ERROR SQL");
    }

    echo json_encode($datos);
} else // FORM NOT SENT
{
    print json_encode(array("status" => "error", "message" => "Formulario no enviado"));
}
