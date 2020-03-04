#include "p2p_server.h"

p2p_server::p2p_server(QObject *parent) : QObject(parent)
{
    udp_send = new QUdpSocket(this);
    udp_send->connectToHost(QHostAddress::Broadcast, 57000);

    udp_get = new QUdpSocket(this);
    udp_get->bind(QHostAddress::Any, 57000);

    connect(udp_get, &QUdpSocket::readyRead, this, &p2p_server::read_data);
    send_data();

}

void p2p_server::read_data(){

    QHostAddress sender;
    quint16 sender_port;
    while(udp_get->hasPendingDatagrams()) {
        QByteArray datagram;
        datagram.resize(udp_get->pendingDatagramSize());
        udp_get->readDatagram(datagram.data(), datagram.size(), &sender, &sender_port);
        qDebug() << "Message :: " << datagram;
        qDebug() << "MEssage from: " << sender.toString();
        qDebug() << "Port" << sender_port;
    }

}

void p2p_server::send_data(){
    QByteArray datagram;
    datagram.append("Hello, test");
    udp_send->writeDatagram(datagram, QHostAddress::LocalHost, 57000);
}
