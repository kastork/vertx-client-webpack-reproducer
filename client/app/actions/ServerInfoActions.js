import alt from '../libs/alt';

class ServerInfoActions {
  
  serverInfoReceived(message) {
    this.dispatch(message);
  }
}

export default alt.createActions(ServerInfoActions);
