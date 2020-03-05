#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include "p2p_server.h"

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();
    QString name, port, ip, ip_port;

private slots:
    void on_send_button_clicked();

private:
    Ui::MainWindow *ui;
    p2p_server *p2p;

    void datagram_ui();
    void set_variables(QString initialise_vars);
};
#endif // MAINWINDOW_H
