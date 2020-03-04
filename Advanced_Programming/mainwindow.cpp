#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    p2p = new p2p_server;

}

MainWindow::~MainWindow()
{
    delete ui;
}


void MainWindow::on_send_button_clicked()
{
    //Takes the text from main input, broadcasts it as a datagram to port 57000
    QString new_message = ui->main_user_input->text();
    //Clears the ui element for next user input.
    ui->main_user_input->clear();
    p2p->send_data(new_message);
}

void MainWindow::read_data(){

}
