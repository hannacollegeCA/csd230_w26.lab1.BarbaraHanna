import axios from 'axios';

const api = axios.create({
    baseURL: '/api/rest'
});

// One client: read storage when request runs
api.interceptors.request.use(config => {

    const token = globalThis.localStorage?.getItem('token');

    if (config.url === '/auth/login') {
        return config;
    }

    if (token) {
        config.headers = config.headers ?? {};
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
});

export default api;
