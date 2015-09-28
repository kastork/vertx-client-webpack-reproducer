
const BusRoutes =  {

  // channels
  server_info: 'com.example:stat:server-info',
  chat_message: 'com.example:chat:chat-message',
  gibber_message: 'com.example:chat:blarf-blarf',

  // verbs
  // TODO: secure these with JWT
  // TODO: show alternative; hit a secured http endpoint
  broadcast_chat: 'com.example:cmd:broadcast-chat',
  poke_server: 'com.example:cmd:poke-server'

  // TODO: private channel prefixes
};


export default BusRoutes;
