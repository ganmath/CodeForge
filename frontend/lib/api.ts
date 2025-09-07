const API_BASE = process.env.NEXT_PUBLIC_API_BASE_URL || 'http://localhost:8080';

export const handleApiCall = async (apiCall: () => Promise<Response>) => {
  try {
    const response = await apiCall();
    if (!response.ok) {
      throw new Error(`API Error: ${response.status}`);
    }
    return await response.json();
  } catch (error) {
    console.error('API call failed:', error);
    throw error;
  }
};

export const pingApi = () => fetch(`${API_BASE}/api/ping`);