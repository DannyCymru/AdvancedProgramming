#include "net_interface.h"

net_interface::net_interface(){

}

//Returns information to deduce network session requirements.
void net_interface::net_required(){
    qDebug() << QNetworkConfigurationManager::NetworkSessionRequired;
}

