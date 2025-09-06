
import React, { useState } from 'react';
import { SafeAreaView, Text, Button } from 'react-native';

const API_BASE = process.env.BACKEND_BASE_URL || 'http://10.0.2.2:8080';

export default function App() {
  const [res, setRes] = useState('');

  const ping = async () => {
    try {
      const r = await fetch(`${API_BASE}/api/ping`);
      const j = await r.json();
      setRes(`status: ${j.status}`);
    } catch (e) {
      setRes('error');
    }
  };

  return (
    <SafeAreaView style={{ padding: 24 }}>
      <Text style={{ fontSize: 24, marginBottom: 16 }}>Spring Boot CRUD Template Mobile</Text>
      <Button title="Ping API" onPress={ping} />
      <Text accessibilityLabel="result">{res}</Text>
    </SafeAreaView>
  );
}
