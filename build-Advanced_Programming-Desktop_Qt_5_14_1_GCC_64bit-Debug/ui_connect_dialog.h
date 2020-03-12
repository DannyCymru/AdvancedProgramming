/********************************************************************************
** Form generated from reading UI file 'connect_dialog.ui'
**
** Created by: Qt User Interface Compiler version 5.14.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_CONNECT_DIALOG_H
#define UI_CONNECT_DIALOG_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QDialog>
#include <QtWidgets/QDialogButtonBox>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>

QT_BEGIN_NAMESPACE

class Ui_connect_dialog
{
public:
    QDialogButtonBox *buttonBox;
    QLabel *label;
    QLabel *label_2;
    QLabel *label_3;
    QLabel *label_4;
    QLineEdit *id_input;
    QLineEdit *open_port_input;
    QLineEdit *ip_input;
    QLineEdit *ip_port_input;

    void setupUi(QDialog *connect_dialog)
    {
        if (connect_dialog->objectName().isEmpty())
            connect_dialog->setObjectName(QString::fromUtf8("connect_dialog"));
        connect_dialog->resize(320, 240);
        buttonBox = new QDialogButtonBox(connect_dialog);
        buttonBox->setObjectName(QString::fromUtf8("buttonBox"));
        buttonBox->setGeometry(QRect(10, 200, 301, 32));
        buttonBox->setOrientation(Qt::Horizontal);
        buttonBox->setStandardButtons(QDialogButtonBox::Cancel|QDialogButtonBox::Ok);
        label = new QLabel(connect_dialog);
        label->setObjectName(QString::fromUtf8("label"));
        label->setGeometry(QRect(30, 40, 58, 18));
        label_2 = new QLabel(connect_dialog);
        label_2->setObjectName(QString::fromUtf8("label_2"));
        label_2->setGeometry(QRect(30, 70, 71, 18));
        label_3 = new QLabel(connect_dialog);
        label_3->setObjectName(QString::fromUtf8("label_3"));
        label_3->setGeometry(QRect(30, 100, 161, 18));
        label_4 = new QLabel(connect_dialog);
        label_4->setObjectName(QString::fromUtf8("label_4"));
        label_4->setGeometry(QRect(30, 130, 58, 18));
        id_input = new QLineEdit(connect_dialog);
        id_input->setObjectName(QString::fromUtf8("id_input"));
        id_input->setGeometry(QRect(120, 40, 113, 26));
        open_port_input = new QLineEdit(connect_dialog);
        open_port_input->setObjectName(QString::fromUtf8("open_port_input"));
        open_port_input->setGeometry(QRect(120, 70, 113, 26));
        ip_input = new QLineEdit(connect_dialog);
        ip_input->setObjectName(QString::fromUtf8("ip_input"));
        ip_input->setGeometry(QRect(120, 100, 113, 26));
        ip_port_input = new QLineEdit(connect_dialog);
        ip_port_input->setObjectName(QString::fromUtf8("ip_port_input"));
        ip_port_input->setGeometry(QRect(120, 130, 113, 26));

        retranslateUi(connect_dialog);
        QObject::connect(buttonBox, SIGNAL(accepted()), connect_dialog, SLOT(accept()));
        QObject::connect(buttonBox, SIGNAL(rejected()), connect_dialog, SLOT(reject()));

        QMetaObject::connectSlotsByName(connect_dialog);
    } // setupUi

    void retranslateUi(QDialog *connect_dialog)
    {
        connect_dialog->setWindowTitle(QCoreApplication::translate("connect_dialog", "Dialog", nullptr));
        label->setText(QCoreApplication::translate("connect_dialog", "User ID:", nullptr));
        label_2->setText(QCoreApplication::translate("connect_dialog", "Your port:", nullptr));
        label_3->setText(QCoreApplication::translate("connect_dialog", "IP Address:", nullptr));
        label_4->setText(QCoreApplication::translate("connect_dialog", "Port:", nullptr));
    } // retranslateUi

};

namespace Ui {
    class connect_dialog: public Ui_connect_dialog {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_CONNECT_DIALOG_H
