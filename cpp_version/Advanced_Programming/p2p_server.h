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
    void send_data(QString user_id, QString new_message);
    QString get_data();
    void read_data();
    void local_socket(int port);
    void ip_socket(int port);

signals:

private:
    QUdpSocket *udp_send;
    QUdpSocket *udp_get;
    net_interface *net_int;


    void group_broadcast();
    QString connect_request(int ip_port);
};

#endif // P2P_SERVER_H
