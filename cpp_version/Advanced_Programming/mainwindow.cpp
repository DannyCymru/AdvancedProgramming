#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    p2p = new p2p_server;

    QString open_message = "Please input your name, your port, the person you want to connect to and thier port like so. dr3344j:57000:192.168.23.4:57000";
     ui->message_edit->appendPlainText(open_message);

     //Timer to constantly check if there has been any messages received
     QTimer *timer = new QTimer(this);
     connect(timer, SIGNAL(timeout()), SLOT(datagram_ui()));
     timer->start(1000);

}

MainWindow::~MainWindow()
{
    delete ui;
}


void MainWindow::on_send_button_clicked()
{
    //Checks if the user has entered their information. This stops the program from crashing or not knowing
    //where to send datagrams.
    QString id = ui->user_id->text();
    QString host = ui->host_list_edit->toPlainText();
    if (id == "user_id" && host == ""){
        try {

            //Updates UI elements.)
            ui->message_edit->clear();

            QString user_input = ui->main_user_input->text();
            ui->message_edit->appendPlainText(user_input);
            ui->main_user_input->clear();
            set_variables(user_input);
            
        } catch (...) {
            ui->message_edit->appendPlainText("Please try again");
        }
    }
    else {
        //Takes the text from main input, broadcasts it as a datagram to port 57000
        QString new_message = ui->main_user_input->text();
        //Clears the ui element for next user input.
        ui->main_user_input->clear();
        p2p->send_data(id, new_message);
    }
}

void MainWindow::datagram_ui(){
    QString data = p2p->get_data();

    //Checks to see if their is any data
    //If not then it does nothing, otherwise it appends the data to the ui.
    if(data.isEmpty()){

    }
    else{
        ui->message_edit->appendPlainText(data);
    }
}

void MainWindow::set_variables(QString initialise_vars){
    //Necessary variables to be recieved from the user input
    //Delimiter to split the text with each instance of the colon
    QStringList delimiter = initialise_vars.split(":");
    //Vector to dump all the different values into after the split.
    QVector<QString> elements;

    //Takes the regular expression and runs through every element printing them with the regEx values found.
    initialise_vars = initialise_vars.replace(QRegularExpression("(\\d):(\\d)"), "\\1:\\2");
    foreach (QString element, delimiter) {
        elements << element.replace(QRegularExpression("(\\d):(\\d)"), "\\1:\\2");
    }

    //If statement to check number of elements inside the vector, then stores the value of each element in the correct variable.
        try {
            if(elements.size() >= 4){
                name = elements[0], port = elements[1],ip = elements[2], ip_port = elements[3];

                //Quick and dirty way to change a few of the UI Elements. Should probably get pushed to their own function.
                ui->user_id->setText(name);
                ui->host_list_edit->appendPlainText(ip+":"+ip_port);

                //Local port
                int port_number = port.toInt();
                p2p->local_socket(port_number);

                //Port of address to connect to.
                int ip_port_number = ip_port.toInt();
                p2p->ip_socket(ip_port_number);

            }
            else {
                ui->message_edit->appendPlainText("You did not input enough values.");
            }
        }
        catch (...) {
            ui->message_edit->appendPlainText("Please try again");
        }
}

//Menu bar -> file -> connect action
void MainWindow::on_actionConnect_triggered(){
  connect_window = new connect_dialog;
  try {
    //If dialog exits in an accepted state then it obtains the information inputted.
    if ( connect_window->exec()== QDialog::Accepted) {
        QVector<QString>v = connect_window->get_data();
        if(v.size() == 4){
        ui->user_id->setText(v[0]);
        ui->host_list_edit->appendPlainText(v[2]+":"+v[3]);
        p2p->local_socket(v[1].toInt());
        p2p->ip_socket(v[3].toInt());
        }
        else{
            ui->message_edit->appendPlainText("Did not fill in all the inputs");
        }
    }
  }
  catch(...){

  }

}

void MainWindow::on_actionClose_triggered(){
}
