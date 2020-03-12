#ifndef CONNECT_DIALOG_H
#define CONNECT_DIALOG_H

#include <QDialog>
#include <QRegExpValidator>

namespace Ui {
class connect_dialog;
}

class connect_dialog : public QDialog
{
    Q_OBJECT

public:
    explicit connect_dialog(QWidget *parent = nullptr);
    ~connect_dialog();
    QVector<QString> get_data();

private slots:
    void on_buttonBox_accepted();

private:
    Ui::connect_dialog *ui;
};

#endif // CONNECT_DIALOG_H
