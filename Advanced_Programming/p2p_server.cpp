#include "p2p_server.h"

p2p_server::p2p_server(QObject *parent) : QObject(parent)
{
    net_int = new net_interface;

    /* Function call to check the required network config.
    net_int->net_required();*/

    //UDP Socket initialisations
    udp_send = new QUdpSocket(this);
    udp_get = new QUdpSocket(this);

    //Binds and connects both sockets to the correct address for sending and recieving data
    udp_send->connectToHost(QHostAddress::Broadcast, 57000);
    udp_get->bind(QHostAddress::AnyIPv4, 57000);

    //Connects the receiver to the correct function and ensures socket is in read mode.
    connect(udp_get, &QUdpSocket::readyRead, this, &p2p_server::read_data);
}

//Reads data from incoming datagrams.
void p2p_server::read_data(){

    QByteArray datagram;
    QHostAddress sender;
    quint16 sender_port;
    while(udp_get->hasPendingDatagrams()) {
        datagram.resize(udp_get->pendingDatagramSize());
        //Reads the QByteArray and places data into the correct objects previously set up
        udp_get->readDatagram(datagram.data(), datagram.size(), &sender, &sender_port);
        //Logs data to console, good for debugging.
        qDebug() << "Message: " << datagram;
        qDebug() << "Message from: " << sender.toString();
        qDebug() << "Port:" << sender_port;
    }

}

//Sends data from button click to network broadcast
void p2p_server::send_data(QString new_message){
    QByteArray datagram;
    datagram.append(new_message);
    udp_send->writeDatagram(datagram, QHostAddress::LocalHost, 57000);
}
