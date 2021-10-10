import axios, {AxiosResponse} from 'axios'

const axiosApi = axios.create({
    headers: { 'Content-Type': 'application/json' }
});

export default {
    getPokemonSpeciesDescription(pokemonSpecies: string): Promise<AxiosResponse> {
        return axiosApi.get(`/pokemon/` + pokemonSpecies);
    }
}
