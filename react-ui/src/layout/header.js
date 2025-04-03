import React, { useCallback } from "react";
import { Layout, Menu, Dropdown } from "antd";
import logo from "@/assets/images/logo.jpg";
import MyIcon from "@/components/icon/";
import { clearLocalDatas } from "@/utils";
import { USER_INFO, TOKEN, MENU, } from "@/common"
import { useDispatchUser, useStateUserInfo } from "@/store/hooks";
import { WebSocketAlert } from "@/pages/websocket/WebSocketAlert.jsx";

const { Header } = Layout;
const RightMenu = ({ logout }) => (
  <Menu className="right-down">
    <Menu.Item
      key="logout"
      onClick={logout}
      icon={<MyIcon type="icon_disconnectdevice" />}
    >
      退出登录
    </Menu.Item>
  </Menu>
);

const getPopupContainer = (HTMLElement) => HTMLElement;

const LayoutHeader = ({ children }) => {
  const userInfo = useStateUserInfo()
  const { stateClearUser: clearStateUser } = useDispatchUser()
  const logout = useCallback(() => {
    clearLocalDatas([USER_INFO, TOKEN, MENU]);
    window.location.reload();
    clearStateUser();
  }, [clearStateUser]);
  return (
    <Header className="header">
      <WebSocketAlert />
      <div className="logo">
        <img src={logo} alt="logo"></img>
        <span>超级飞侠金服</span>
      </div>
      {children}
      <div className="right">
        <Dropdown
          placement="bottomCenter"
          getPopupContainer={getPopupContainer}
          overlay={<RightMenu logout={logout} />}
        >
          <div>
            {userInfo.identityLevel === 1|| userInfo.identityLevel === 0? '客户' :
              userInfo.identityLevel === 2 ? '客户经理' :
                userInfo.identityLevel === 3 ? '风控人员' :
                  userInfo.identityLevel === 4 ? '合规人员' : '未知'}:{userInfo.phoneNumber}
          </div></Dropdown>
      </div>
    </Header>
  );
};
export default LayoutHeader
