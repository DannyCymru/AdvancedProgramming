/********************************************************************************
** Form generated from reading UI file 'mainwindow.ui'
**
** Created by: Qt User Interface Compiler version 5.14.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MAINWINDOW_H
#define UI_MAINWINDOW_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenu>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QPlainTextEdit>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_MainWindow
{
public:
    QAction *actionConnect;
    QAction *actionClose;
    QWidget *centralwidget;
    QPlainTextEdit *message_edit;
    QLineEdit *main_user_input;
    QLabel *user_id;
    QPushButton *send_button;
    QPlainTextEdit *connected_users_edit;
    QPlainTextEdit *host_list_edit;
    QMenuBar *menubar;
    QMenu *menuFile;
    QMenu *menuHelp;
    QMenu *menuAbout;

    void setupUi(QMainWindow *MainWindow)
    {
        if (MainWindow->objectName().isEmpty())
            MainWindow->setObjectName(QString::fromUtf8("MainWindow"));
        MainWindow->resize(1079, 578);
        actionConnect = new QAction(MainWindow);
        actionConnect->setObjectName(QString::fromUtf8("actionConnect"));
        actionClose = new QAction(MainWindow);
        actionClose->setObjectName(QString::fromUtf8("actionClose"));
        centralwidget = new QWidget(MainWindow);
        centralwidget->setObjectName(QString::fromUtf8("centralwidget"));
        message_edit = new QPlainTextEdit(centralwidget);
        message_edit->setObjectName(QString::fromUtf8("message_edit"));
        message_edit->setGeometry(QRect(170, 0, 731, 511));
        message_edit->setReadOnly(true);
        main_user_input = new QLineEdit(centralwidget);
        main_user_input->setObjectName(QString::fromUtf8("main_user_input"));
        main_user_input->setGeometry(QRect(220, 520, 681, 21));
        user_id = new QLabel(centralwidget);
        user_id->setObjectName(QString::fromUtf8("user_id"));
        user_id->setGeometry(QRect(170, 520, 51, 20));
        send_button = new QPushButton(centralwidget);
        send_button->setObjectName(QString::fromUtf8("send_button"));
        send_button->setGeometry(QRect(910, 520, 161, 21));
        connected_users_edit = new QPlainTextEdit(centralwidget);
        connected_users_edit->setObjectName(QString::fromUtf8("connected_users_edit"));
        connected_users_edit->setGeometry(QRect(910, 0, 161, 511));
        connected_users_edit->setReadOnly(true);
        host_list_edit = new QPlainTextEdit(centralwidget);
        host_list_edit->setObjectName(QString::fromUtf8("host_list_edit"));
        host_list_edit->setGeometry(QRect(10, 0, 151, 541));
        host_list_edit->setReadOnly(true);
        MainWindow->setCentralWidget(centralwidget);
        menubar = new QMenuBar(MainWindow);
        menubar->setObjectName(QString::fromUtf8("menubar"));
        menubar->setGeometry(QRect(0, 0, 1079, 23));
        menuFile = new QMenu(menubar);
        menuFile->setObjectName(QString::fromUtf8("menuFile"));
        menuHelp = new QMenu(menubar);
        menuHelp->setObjectName(QString::fromUtf8("menuHelp"));
        menuAbout = new QMenu(menubar);
        menuAbout->setObjectName(QString::fromUtf8("menuAbout"));
        MainWindow->setMenuBar(menubar);

        menubar->addAction(menuFile->menuAction());
        menubar->addAction(menuHelp->menuAction());
        menubar->addAction(menuAbout->menuAction());
        menuFile->addSeparator();
        menuFile->addAction(actionConnect);
        menuFile->addAction(actionClose);

        retranslateUi(MainWindow);
        QObject::connect(actionConnect, SIGNAL(triggered()), MainWindow, SLOT(show()));
        QObject::connect(actionClose, SIGNAL(triggered()), MainWindow, SLOT(close()));

        QMetaObject::connectSlotsByName(MainWindow);
    } // setupUi

    void retranslateUi(QMainWindow *MainWindow)
    {
        MainWindow->setWindowTitle(QCoreApplication::translate("MainWindow", "MainWindow", nullptr));
        actionConnect->setText(QCoreApplication::translate("MainWindow", "Connect", nullptr));
        actionClose->setText(QCoreApplication::translate("MainWindow", "Close", nullptr));
#if QT_CONFIG(shortcut)
        actionClose->setShortcut(QCoreApplication::translate("MainWindow", "Ctrl+C", nullptr));
#endif // QT_CONFIG(shortcut)
        user_id->setText(QCoreApplication::translate("MainWindow", "user_id", nullptr));
        send_button->setText(QCoreApplication::translate("MainWindow", "send", nullptr));
        menuFile->setTitle(QCoreApplication::translate("MainWindow", "File", nullptr));
        menuHelp->setTitle(QCoreApplication::translate("MainWindow", "Help", nullptr));
        menuAbout->setTitle(QCoreApplication::translate("MainWindow", "About", nullptr));
    } // retranslateUi

};

namespace Ui {
    class MainWindow: public Ui_MainWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MAINWINDOW_H
