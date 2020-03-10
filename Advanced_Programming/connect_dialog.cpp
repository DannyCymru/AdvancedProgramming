#include "connect_dialog.h"
#include "ui_connect_dialog.h"

connect_dialog::connect_dialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::connect_dialog)
{
    ui->setupUi(this);

    //Validation technique to make sure user only inputs a usable ip address
    QString ip_range = "(?:[0-1]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])";
    QRegExp ip_regex ("^" + ip_range + "\\." + ip_range + "\\." + ip_range + "\\." + ip_range + "$");
    QRegExpValidator *ip_validator = new QRegExpValidator(ip_regex, this);
    ui->ip_input->setValidator(ip_validator);

}

connect_dialog::~connect_dialog()
{
    delete ui;
}

void connect_dialog::on_buttonBox_accepted()
{

}


QVector<QString> connect_dialog::get_data(){

    //Gets all text that was inputted by the user
    QString id_value = ui->id_input->text(),
            my_port = ui->open_port_input->text(),
            ip_value= ui->ip_input->text(),
            ip_port_value = ui->ip_port_input->text();
    QVector<QString>v;

    try {
        //Makes sure that information only gets changed if all inputs where filled in
        if(id_value == "" && my_port == "" && ip_value == "" && ip_port_value == ""){

            QDialog::setResult(0);

        }
        else {
            //Fills info into a vector that is then returned to the main program
            v = {
                {id_value},
                {my_port},
                {ip_value},
                {ip_port_value}
            };

        }
    } catch (...) {

    }

    return v;
}
