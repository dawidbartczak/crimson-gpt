import AxiosInstance from "../AxiosInstance";



const get = async () => {
    const response = await AxiosInstance.get(`http://localhost:8080/api/v1/users`);

    return response;
}



export default { get }