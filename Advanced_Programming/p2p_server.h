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
    void read_data();
    void send_data(QString new_message);

signals:

private:
    QUdpSocket *udp_send;
    QUdpSocket *udp_get;

    net_interface *net_int;
};

#endif // P2P_SERVER_H
