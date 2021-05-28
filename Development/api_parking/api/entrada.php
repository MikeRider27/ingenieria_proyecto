<?php
session_start();
include('../core/connection.php');
$dbconn = getConnection();

// Check if the form was sent and the action is SAVE
if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "insert") {
    //terminado
    $result = 0;
    $id_entrada_salida = 0;
    $chapa = mb_strtoupper(trim($_REQUEST['chapa']), 'UTF-8');
    $fecha_entrada = $_REQUEST['fecha_entrada'];
    $hora_entrada = $_REQUEST['hora_entrada'];
    $id_bahia = $_REQUEST['id_bahia'];
    $observaciones = $_REQUEST['observaciones'];
    $estado = 'INGRESO';
    $id_usuario = $_REQUEST['id_usuario'];

    try {
        $stmt2 = $dbconn->prepare("SELECT * FROM entrada_salida WHERE chapa = :chapa AND estado ='INGRESO'");
        $stmt2->bindValue(":chapa", $chapa);
        $stmt2->execute();
        $entrada = $stmt2->fetch(PDO::FETCH_ASSOC);
        if (!empty($entrada['chapa'])) {
            $message = "Ya se encuentra registrado una entrada con esa chapa = " . $chapa;
            $status = "error";
            print json_encode(array("status" => $status, "message" => $message));
            exit();
        } else {
            $sql = 'INSERT INTO entrada_salida (chapa, fecha_entrada, hora_entrada, id_bahia, observaciones, estado, id_usuario)
					VALUES (:chapa, :fecha_entrada, :hora_entrada, :id_bahia, :observaciones, :estado, :id_usuario)';
            $stmt = $dbconn->prepare($sql);
            $stmt->bindValue(':chapa', $chapa);
            $stmt->bindValue(':fecha_entrada', $fecha_entrada);
            $stmt->bindValue(':hora_entrada', $hora_entrada);
            $stmt->bindValue(':id_bahia', $id_bahia);
            $stmt->bindValue(':observaciones', $observaciones);
            $stmt->bindValue(':estado', $estado);
            $stmt->bindValue(':id_usuario', $id_usuario);
            $result = $stmt->execute();
            $id_entrada_salida = $dbconn->lastInsertId();
            $message = $result ? "Se registró correctamente la entrada" :
                "Ocurrio un error intentado resolver la solicitud";
            $status = $result ? "success" : "error";
            print json_encode(array("status" => $status, "message" => $message));
        }
    } catch (Exception $e) {
        $result = FALSE;
        var_dump($e->getMessage());
    }
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "update") {
    //terminado
    $result = 0;
    $id_entrada_salida = $_REQUEST['id_entrada_salida'];
    $fecha_salida = $_REQUEST['fecha_salida'];
    $hora_salida = $_REQUEST['hora_salida'];
    $tiempo_total = $_REQUEST['tiempo_total'];
    if (!empty($id_entrada_salida)) {
        $sql = 'UPDATE entrada_salida SET 
        fecha_salida= :fecha_salida,
        hora_salida= :hora_salida,
        tiempo_total= :tiempo_total 
        WHERE id_entrada_salida = :id_entrada_salida';
        $stmt = $dbconn->prepare($sql);
        $stmt->bindValue(':id_entrada_salida', $id_entrada_salida);
    }
    $stmt->bindValue(':fecha_salida', $fecha_salida);
    $stmt->bindValue(':hora_salida', $hora_salida);
    $stmt->bindValue(':tiempo_total', $tiempo_total);
    $result = $stmt->execute();
    $message = $result ? "Registro de la salida exitosamente!" :
        "Ocurrio un error intentado resolver la solicitud";
    $status = $result ? "success" : "error";
    print json_encode(array("status" => $status, "message" => $message));
} else // FORM NOT SENT
{
    print json_encode(array("status" => "error", "message" => "Formulario no enviado"));
}
