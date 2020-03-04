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
    //Takes the text from main input.
    QString new_message = ui->main_user_input->text();
    ui->main_user_input->clear();
    p2p->send_data(new_message);
}
