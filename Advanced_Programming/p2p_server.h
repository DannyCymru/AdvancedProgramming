#ifndef P2P_SERVER_H
#define P2P_SERVER_H

#include <QObject>
#include <QtNetwork>
#include <QUdpSocket>
class p2p_server : public QObject
{
    Q_OBJECT
public:
    explicit p2p_server(QObject *parent = nullptr);
    void read_data();

signals:

private:
    QUdpSocket *udp_socket;
    void send_data();
};

#endif // P2P_SERVER_H
