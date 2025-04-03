import ajax from "@/common/ajax";
import mock from "../mock/index";
const request = process.env.REACT_APP_MOCK === "1" ? mock : ajax;
const getMenu = () => mock.get("/getmenu");
const getMenuList = () => mock.get("/getmenulist");
const login = (data) => request.post("/v1/auth/pwdLogin", data);
const addMenu = (data) => request.post("/addmenu", data);
const addMsg = (id, data) => request.post(`/v1/auth/register?advisorId=${id}`, data);
const getMsg = (id) => request.get(`v1/client/getClientsByAdvisorId?advisorId=${id}`);
const getApprovalList = (id) => request.get(`/v1/approval/getApprovalList?userId=${id}`);
const getRecommendList = (id) => request.post(`/v1/advisory/getRecommendItemsByClientId?clientId=${id}`);
const createRecommendList = (clientId,userId) => request.post(`/v1/advisory/creatRecommendList?clientId=${clientId}&userId=${userId}`);
const addTradeMsg = (data) => request.post("/addmessage", data);
const getTradeMsg = () => request.get(`/v1/trade/getTradesByClientId?clientId=${JSON.parse(localStorage.getItem('USER_INFO')).clientId}`);
const approval = (approvalId, userId) => request.post(`/v1/approval/approval?approvalId=${approvalId}&userId=${userId}`);
const getPower = () => request.get("/getpower");
const delMenu = (data) => request.post("/delmenu", data);
const getMenuInfo = (data) => request.get("/getmenuinfo", data);
const editMenu = (data) => request.post("/editmenuinfo", data);
const getVisitorList = (data) => request.get("/getiplist", data);
const getVisitorData = () => request.get("/getvisitordata");
const getUserList = (data) => request.get("/getuserlist", data);
const addUser = (data) => request.post("/adduserinfo", data);
const getUser = (data) => request.get("/getuserinfo", data);
const editUser = (data) => request.post("/edituserinfo", data);
const editType = (data) => request.post("/edittype", data);
const addType = (data) => request.post("/addtype", data);
const getFeedBack = (data) => request.post("/getfeedback", data);
const reply = (data) => request.post("/reply", data);
const portfoliosclient = (data) => request.get(`/v1/portfolios/client/${data.clientId}/average-value-last-seven-days`);
const portfoliosclientnew = (data) => request.get(`/v1/portfolios/client/${data.clientId}/average-value-last-seven-days-new`);
const topfiveportfolio = (data) => request.get(`/v1/portfolios/client/${data.clientId}/top-five-portfolio-returns`);
const topfiverisk = (data) => request.get(`/v1/portfolios/client/${data.clientId}/top-five-risk-ratios`);
const topratios = (data) => request.get(`/v1/portfolios/${data.clientId}/ratios`);
const getThreeInfo = (data) => request.get(`/v1/client/getThreeInfo?clientId=${data.clientId}`);
const getApprovalsByClientId = (data) => request.get(`/v1/client/export?advisorId=${data.userId}&format=excel`);
const createTrades = (recommendationId) => request.post(`/v1/advisory/createTrades?recommendationId=${recommendationId}`);
const saveUserimp = (data) => request.post(`/v1/risk/info/save`,data);
const riskslist = (data) => request.post(`/v1/risk/info/listAll`,data);
const risksave = (data) => request.post(`/v1/risk/info/check`,data);



export {
  getMenu,
  login,
  addMenu,
  addMsg,
  getMsg,
  getApprovalList,
  getRecommendList,
  createRecommendList,
  addTradeMsg,
  getTradeMsg,
  approval,
  getPower,
  delMenu,
  getMenuInfo,
  editMenu,
  getVisitorList,
  getVisitorData,
  getUserList,
  addUser,
  getUser,
  editUser,
  editType,
  addType,
  getMenuList,
  getFeedBack,
  reply,
  portfoliosclient,
  portfoliosclientnew,
  topfiveportfolio,
  topfiverisk,
  topratios,
  getThreeInfo,
  getApprovalsByClientId,
  createTrades,
  saveUserimp,
  riskslist,
  risksave
};
