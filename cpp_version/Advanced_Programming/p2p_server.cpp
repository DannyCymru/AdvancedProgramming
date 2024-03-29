#include "p2p_server.h"

QByteArray datagram;

p2p_server::p2p_server(QObject *parent) : QObject(parent)
{
    /* Function call to check the required network config.
    net_int = new net_interface;
    net_int->net_required();*/
}

//Binds and connects both sockets to the correct address and user
//inputed port to send and recieving data
void p2p_server::ip_socket(int port){
    udp_send = new QUdpSocket(this);
    udp_send->connectToHost(QHostAddress::Broadcast, port);
}

void p2p_server::local_socket(int port){

    udp_get = new QUdpSocket(this);
    udp_get->bind(QHostAddress::AnyIPv4, port);

    //Connects the receiver to the correct function and ensures socket is in read mode.
    connect(udp_get, &QUdpSocket::readyRead, this, &p2p_server::read_data);
}

void p2p_server::group_broadcast(){

}

QString p2p_server::connect_request(int ip_port){

}

//Reads data from incoming datagrams.
void p2p_server::read_data(){
    QHostAddress sender;
    quint16 sender_port;
    while(udp_get->hasPendingDatagrams()) {
        datagram.resize(udp_get->pendingDatagramSize());
        //Reads the QByteArray and places data into the correct objects previously set up
        udp_get->readDatagram(datagram.data(), datagram.size(), &sender, &sender_port);
        //Logs data to console for debugging.
        qDebug() << "Datagram: " << datagram;
        qDebug() << "IP: " << sender.toString();
        qDebug() << "Port:" << sender_port;
    }
}

QString p2p_server::get_data(){
    QString old_data = datagram;
    datagram.clear();
    return old_data;
}

//Sends data from button click to network broadcast
void p2p_server::send_data(QString user_id, QString new_message){
    QByteArray datagram;
    datagram.append(user_id + ":" + new_message);
    udp_send->writeDatagram(datagram, QHostAddress::Broadcast, 57000);
}
