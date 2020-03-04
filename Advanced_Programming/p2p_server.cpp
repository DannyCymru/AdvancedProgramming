#include "p2p_server.h"

p2p_server::p2p_server(QObject *parent) : QObject(parent)
{
    udp_socket = new QUdpSocket(this);
    udp_socket->bind(QHostAddress::Any, 57000);

    connect(udp_socket, &QUdpSocket::readyRead, this, &p2p_server::read_data);

}

void p2p_server::read_data(){
    while(udp_socket->hasPendingDatagrams()) {
        QByteArray datagram;
        datagram.resize(udp_socket->pendingDatagramSize());
        udp_socket->readDatagram(datagram.data(), datagram.size());
        qDebug() << "Message :: " << datagram;
    }

}

void p2p_server::send_data(){

}
