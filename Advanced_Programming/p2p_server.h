#ifndef P2P_SERVER_H
#define P2P_SERVER_H

#include <QObject>
#include <QtNetwork>
#include <QUdpSocket>
#include "net_interface.h"

class p2p_server : public QObject
{
    Q_OBJECT
public:
    explicit p2p_server(QObject *parent = nullptr);
    void send_data(QString new_message);
    QString get_data();
    void read_data();

signals:

private:
    QUdpSocket *udp_send;
    QUdpSocket *udp_get;
    net_interface *net_int;

    void output_socket();
    void input_socket();
    void group_broadcast();
    void connect_request();
};

#endif // P2P_SERVER_H
