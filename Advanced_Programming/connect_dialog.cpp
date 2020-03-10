#include "connect_dialog.h"
#include "ui_connect_dialog.h"

connect_dialog::connect_dialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::connect_dialog)
{
    ui->setupUi(this);
}

connect_dialog::~connect_dialog()
{
    delete ui;
}

void connect_dialog::on_buttonBox_accepted()
{

}


QVector<QString> connect_dialog::get_data(){


    QString id_value = ui->id_input->text();
    QString my_port = ui->open_port_input->text();
    QString ip_value= ui->ip_input->text();
    QString ip_port_value = ui->ip_port_input->text();
    QVector<QString>v = {
        {id_value},
        {my_port},
        {ip_value},
        {ip_port_value}
    };
    return v;
}
